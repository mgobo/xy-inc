/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.facade.impl.test;

import com.zup.br.dao.api.IZupGenericMethods;
import com.zup.br.dao.impl.ProductsDao;
import com.zup.br.domain.Products;
import com.zup.br.facade.impl.ProductsFacade;
import static com.zup.br.facade.impl.test.BaseIntegrationTest.defaultDeployment;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author mateusgobo
 */
@RunWith(Arquillian.class)
public class ProductsFacadeTest extends BaseIntegrationTest{
    
    @Deployment
    public static Archive<?> createDeployment(){
        JavaArchive archive = defaultDeployment();
        archive.addClasses(IZupGenericMethods.class,
                           ProductsDao.class,
                           ProductsFacade.class);
        return archive;
    }
    
    private @Inject ProductsFacade productsFacade;

    @Test
    public void salvar() {
        try{
            Products p = new Products();
            p.setCategory("Cat-1");
            p.setDescription("Descr-1");
            p.setPrice(new Double("1.0"));
            p.setName("Prod-1");
            
            p = (Products) productsFacade.salvar(p);
            Assert.assertEquals("---SUCESSO NO TESTE DE REGISTRO DE PRODUTO---", true, p.getId()>0);
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void excluir(Object o) throws Exception {
        try{
            List<Products> listProducts = (List<Products>) this.productsFacade.recuperarDados();
            Products p = listProducts.isEmpty() ? null : listProducts.get(0);
            if(p != null){  
                this.productsFacade.excluir(p);
                Assert.assertEquals("---SUCESSO NO TESTE DE EXCLUSAO DE REGISTRO DE PRODUTO---", true, p.getId()>0);
            }else{
                Assert.assertEquals("---NENHUM REGISTRO ENCONTRADO PARA TESTE DE EXCLUSAO---", true, p==null);
            }
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void recuperarPorId() throws Exception {
        try{
            Products p = this.productsFacade.recuperarPorId(new Long(1));
            if(p != null){  
                Assert.assertEquals("---SUCESSO NO TESTE DE EXCLUSAO DE REGISTRO DE PRODUTO---", true, p.getId()>0);
            }else{
                Assert.assertEquals("---NENHUM REGISTRO ENCONTRADO PARA TESTE DE BUSCA POR ID---", true, p==null);
            }
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void recuperarDados() throws Exception {
        try{
            List<Products> listProducts = (List<Products>) this.productsFacade.recuperarDados();
            if(!listProducts.isEmpty()){  
                Assert.assertEquals("---SUCESSO NO TESTE DE EXCLUSAO DE REGISTRO DE PRODUTO---", true, !listProducts.isEmpty());
            }else{
                Assert.assertEquals("---NENHUM REGISTRO ENCONTRADO PARA TESTE DE LISTAGEM DE DADOS---", true, listProducts.isEmpty());
            }
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }
}
