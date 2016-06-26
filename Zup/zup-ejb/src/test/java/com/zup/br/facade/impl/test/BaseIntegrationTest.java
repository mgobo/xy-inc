/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.facade.impl.test;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import com.zup.br.api.IZup;
import com.zup.br.dao.api.IZupDao;
import com.zup.br.dao.impl.ZupDao;
import com.zup.br.impl.ZupBean;
import com.zup.br.produtor.EntityProdutor;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author cassiothadeu
 */
@RunWith(Arquillian.class)
public class BaseIntegrationTest {

    protected static JavaArchive defaultDeployment() {
        JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(EntityProdutor.class,
                            IZup.class,
                            IZupDao.class,
                            ZupBean.class,
                            ZupDao.class)
                .addAsManifestResource("test-persistence.xml", "persistence.xml")
                .addAsManifestResource("test-beans.xml", "beans.xml");
        return archive;
    }
    
    @Test
    public void initialize(){
        Logger.getLogger(BaseIntegrationTest.class).info("ARQUILIAN STARTED");
    }
}
