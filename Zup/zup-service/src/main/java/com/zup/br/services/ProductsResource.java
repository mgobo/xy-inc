/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.services;

import com.zup.br.domain.Products;
import com.zup.br.facade.impl.ProductsFacade;
import com.zup.br.services.commons.CommonsResponseRS;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author mateusgobo
 */
@Path("/")
@RequestScoped
public class ProductsResource extends CommonsResponseRS{

    @Inject private ProductsFacade productsFacade;
    @Context private UriInfo context;

    /**
     * Creates a new instance of ProcutsResource
     */
    public ProductsResource() {
    }

    /**
     * Retrieves representation of an instance of com.zup.br.services.ProductsResource
     * @return an instance of com.zup.br.domain.Products
     */
    @GET
    @Path("products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        Response response = null;
        try {
            List<Products> products = this.productsFacade.recuperarDados();
            if(products.isEmpty()){
                response = Response.status(Response.Status.ACCEPTED).encoding("ISO-8859-1").entity("SEM REGISTROS DE PRODUTOS").build();
            }else{
                String jsonResponse = this.jsonResponseString(products);
                response = Response.status(Response.Status.OK).encoding("ISO-8859-1").entity(jsonResponse).build();
            }
        } catch (Exception ex) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).encoding("ISO-8859-1").entity("RECURSO INDISPONVIEL").build();
        }
        return response;
    }
    
    @GET
    @Path("products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response product(@PathParam("id") String id) {
        Response response = null;
        try {
            Products p = this.productsFacade.recuperarPorId(new Long(id));
            if(p == null){
                response = Response.status(Response.Status.ACCEPTED).encoding("ISO-8859-1").entity("NENHUM REGISTRO ENCONTRADO PARA O CODIGO "+id).build();
            }else{
                response = Response.status(Response.Status.OK).encoding("ISO-8859-1").entity(p).build();
            }
        } catch (Exception ex) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).encoding("ISO-8859-1").entity("RECURSO INDISPONVIEL").build();
        }
        return response;
    }
    
    /**
     * PUT method for updating or creating an instance of ProductsResource
     * @param p
     * @return 
     */
    @PUT
    @Path("product")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editProduct(Products p) {
        Response response = null;
        try {
            p = (Products) this.productsFacade.salvar(p);
            if(p.getId() > 0){
                response = Response.status(Response.Status.OK).encoding("ISO-8859-1").entity("PRODUTO "+p.getName()+" REGISTRADO!").build();
            }else{
                response = Response.status(Response.Status.OK).encoding("ISO-8859-1").entity("FALHA AO REGISTRAR PRODUTO "+p.getName()).build();
            }
        } catch (Exception ex) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).encoding("ISO-8859-1").entity("RECURSO INDISPONVIEL").build();
        }
        return response;
    }
    
    /**
     * DELETE method for remove an instance of ProductsResource
     * @param id
     * @return 
     */
    @DELETE
    @Path("products/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeProduct(@PathParam("id") String id) {
        Response response = null;
        try {
            Products p = this.productsFacade.recuperarPorId(new Long(id));
            boolean result = this.productsFacade.excluir(p);
            if(result){
                response = Response.status(Response.Status.OK).encoding("ISO-8859-1").entity("REGISTRO "+p.getName()+" REMOVIDO COM SUCESSO!").build();
            }else{
                response = Response.status(Response.Status.ACCEPTED).encoding("ISO-8859-1").entity("PRODUTO "+p.getName()+" NAO ENCONTRADO").build();
            }
        } catch (Exception ex) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).encoding("ISO-8859-1").entity("RECURSO INDISPONVIEL").build();
        }
        return response;
    }
}
