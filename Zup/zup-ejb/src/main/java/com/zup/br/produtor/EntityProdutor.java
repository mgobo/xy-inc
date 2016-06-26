/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.produtor;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mateusgobo
 */
public class EntityProdutor {

    @Produces
    @PersistenceContext(name = "ZUPPU")
    private EntityManager em;
    
}
