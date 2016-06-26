/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.dao.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import com.zup.br.dao.api.IZupDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mateusgobo
 */
public class ZupDao implements IZupDao {

    private @Inject EntityManager em;
    private @Inject UserTransaction utx;

    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery criteriaQuery;
    private Root root;
    
    @Override
    public Object salvar(Object o) throws Exception{
        Object save = null;
        try {
            this.utx.begin();
            save = this.em.merge(o);
            this.commit();
            
            return save;
        } catch (NotSupportedException | SystemException e) {
            this.rollback();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean excluir(Object o) throws Exception {
        try {
            this.utx.begin();
            Object aux = this.em.merge(o);
            this.em.remove(aux);
            this.commit();
            
            return true;
        } catch (NotSupportedException | SystemException e) {
            this.rollback();
            throw new Exception(e.getMessage());
        }
    }

    private void commit() {
        try {
            this.utx.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException ex) {
            Logger.getLogger(ZupDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rollback() {
        try {
            this.utx.rollback();
        } catch (IllegalStateException | SecurityException | SystemException ex1) {
            Logger.getLogger(ZupDao.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    @Override
    public Root from(Class clazz){
        this.root = this.criteriaQuery().from(clazz);
        return root;
    }
    
    @Override
    public CriteriaBuilder criteriaBuilder() {
        if(this.criteriaBuilder == null){
            this.criteriaBuilder = this.em.getCriteriaBuilder();
        }
        return this.criteriaBuilder;
    }

    @Override
    public CriteriaQuery criteriaQuery() {
        if(this.criteriaQuery == null){
            this.criteriaQuery = this.criteriaBuilder().createQuery();
        }
        return this.criteriaQuery;
    }
}
