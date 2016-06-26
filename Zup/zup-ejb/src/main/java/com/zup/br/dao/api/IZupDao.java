/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.dao.api;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mateusgobo
 */
public interface IZupDao {
    
    Object salvar(Object o) throws Exception;
    boolean excluir(Object o) throws Exception;
    
    Root from(Class clazz);
    CriteriaBuilder criteriaBuilder();
    CriteriaQuery criteriaQuery();
    
}
