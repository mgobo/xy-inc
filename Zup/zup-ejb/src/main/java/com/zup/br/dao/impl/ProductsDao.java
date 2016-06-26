/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import com.zup.br.dao.api.IZupDao;
import com.zup.br.dao.api.IZupGenericMethods;
import com.zup.br.domain.Products;
import com.zup.br.domain.Products_;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author mateusgobo
 */
public class ProductsDao implements IZupGenericMethods{
    
    @Inject private EntityManager em;
    @Inject private IZupDao iZupDao;

    public Object salvar(Object o) throws Exception {
        return iZupDao.salvar(o);
    }

    public boolean excluir(Object o) throws Exception {
        return iZupDao.excluir(o);
    }
 
    @Override
    public Products recuperarPorId(Long id) throws Exception{
        try{
            Products p = null;
            Root<Products> from     = this.iZupDao.from(Products.class);
            Predicate p1            = this.iZupDao.criteriaBuilder().equal(from.get(Products_.id), id);
            TypedQuery<Products> tq = this.em.createQuery(this.iZupDao.criteriaQuery().select(from).where(p1));
            
            List<Products> result   = tq.getResultList();
            if(!result.isEmpty()){
                p = result.get(0);
            }
            return p;
        }catch(Exception e){
            throw new Exception("FALHA NA LEITURA DOS DADOS, [ERRO] ==> "+e);
        }
    }

    @Override
    public List recuperarDados() throws Exception {
        try{
            Root<Products> from   = this.iZupDao.from(Products.class);
            TypedQuery<Object> tq = this.em.createQuery(this.iZupDao.criteriaQuery().select(from));
            
            return tq.getResultList();
        }catch(Exception e){
            throw new Exception("FALHA NA LEITURA DOS DADOS, [ERRO] ==> "+e);
        }
    }
}
