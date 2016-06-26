/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.services.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateusgobo
 */
public class CommonsResponseRS {
    
    protected String jsonResponseString(Object o){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());
        mapper.setVisibilityChecker(mapper.getSerializationConfig()
                                .getDefaultVisibilityChecker()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
        mapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        String json     = null;
        try {
            json = mapper.writeValueAsString(o);
        } catch (IOException ex) {
            Logger.getLogger(CommonsResponseRS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
