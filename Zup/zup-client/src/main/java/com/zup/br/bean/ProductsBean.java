/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.br.client.ProductsRS;
import com.zup.br.commons.FacesUtil;
import com.zup.br.domain.Products;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;

/**
 *
 * @author mateusgobo
 */
@ViewScoped
@ManagedBean(name = "productsBean")
public class ProductsBean extends FacesUtil implements Serializable {

    private Products products;
    private List<Products> listProducts;
    private String msg;
    private String price;
    
    private ProductsRS productsRS;

    @PostConstruct
    public void initialize() {
        this.recuperarProducts();
    }
    
    public void novoProduto(){
        this.setProducts(null);
        this.getProducts();
        this.setPrice("");
        this.primeUpdateContent(this.getPrimeRequest(), "formEditProduct");
    }

    public void salvar() {
        this.getProducts().setPrice(new Double(this.getPrice()));
        Response response = this.getProductsRS().editProduct(this.getProducts());
        this.setMsg(response.readEntity(String.class));
        
        this.getListProducts().clear();
        this.recuperarProducts();
        this.primeUpdateContent(this.getPrimeRequest(), "productsDataTable","msg");
    }
    
    public void remover(Long id){
        Response response = this.getProductsRS().removeProduct(id.toString());
        this.setMsg(response.readEntity(String.class));
        
        this.getListProducts().clear();
        this.recuperarProducts();
        this.primeUpdateContent(this.getPrimeRequest(), "productsDataTable","msg");
    }
    
    public void editProduct(Long id){
        String value = this.getProductsRS().product(id.toString()).readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setProducts(mapper.readValue(value, Products.class));
            this.setPrice(this.getProducts().getPrice().toString());
            this.primeUpdateContent(this.getPrimeRequest(), "formEditProduct");
        } catch (IOException ex) {
            Logger.getLogger(ProductsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recuperarProducts() {
        Response response = this.getProductsRS().list(Response.class);
        List list = null;
        switch (response.getStatus()) {
            case 202:
                this.setMsg(response.readEntity(String.class));
                break;
            case 200:
                String jsonList = response.readEntity(String.class);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    list =  mapper.readValue(jsonList, List.class);
                } catch (IOException ex) {
                    Logger.getLogger(ProductsBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(list !=null && !list.isEmpty()){
                    this.getListProducts().addAll(list);
                    this.setMsg("FORAM ENCONTRADOS "+list.size()+" PRODUTOS");
                }
                break;
            case 500:
                this.setMsg(response.readEntity(String.class));
                break;
            default:
                break;
        }
    }

    public void fechar() {
        this.setProducts(null);
        this.getProducts();
        this.setPrice(null);
        this.primeUpdateContent(this.getPrimeRequest(), "formEditProduct");
    }

    public List<Products> getListProducts() {
        if (this.listProducts == null) {
            this.listProducts = new ArrayList<>();
        }
        return listProducts;
    }

    public void setListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }

    public String getMsg() {
        if (this.msg == null) {
            this.msg = "";
        }
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Products getProducts() {
        if (this.products == null) {
            this.products = new Products();
        }
        return products;
    }

    public void setProducts(Products produts) {
        this.products = produts;
    }

    public String getPrice() {
        if (this.price == null) {
            this.price = "";
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private ProductsRS getProductsRS() {
        if(this.productsRS == null){
            this.productsRS = new ProductsRS();
        }
        return productsRS;
    }
}
