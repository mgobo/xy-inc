/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.log4j.Logger;

/**
 *
 * @author martin
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ZupBean {
    
    private final Logger logger = Logger.getLogger(ZupBean.class);

    @PostConstruct
    public void initialize() {
        this.logger.info("INICIALIZANDO APLICACAO");
    }

}
