/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.facade.impl;

import com.zup.br.dao.impl.ProductsDao;
import com.zup.br.domain.Products;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author mateusgobo
 */
public class ProductsFacade {
    
    private @Inject ProductsDao productsDao;

    public Object salvar(Object o) throws Exception {
        return productsDao.salvar(o);
    }

    public boolean excluir(Object o) throws Exception {
        return productsDao.excluir(o);
    }

    public Products recuperarPorId(Long id) throws Exception {
        return productsDao.recuperarPorId(id);
    }

    public List recuperarDados() throws Exception {
        return productsDao.recuperarDados();
    }   
}
