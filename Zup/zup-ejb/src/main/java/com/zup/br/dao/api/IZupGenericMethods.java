/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.dao.api;

import java.util.List;

/**
 *
 * @author mateusgobo
 */
public interface IZupGenericMethods {
    
    Object recuperarPorId(Long id) throws Exception;
    List recuperarDados() throws Exception;
    
}
