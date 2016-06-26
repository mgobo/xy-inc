/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:ProductsResource [/]<br>
 * USAGE:
 * <pre>
        ProductsRS client = new ProductsRS();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author mateusgobo
 */
public class ProductsRS {

    private static final String BASE_URI = "http://localhost:8080/zup-service/";

    public ProductsRS() {
    }

    public Response product(String id) throws ClientErrorException {
        Client client       = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI);
        WebTarget resource  = webTarget;
        resource = resource.path(java.text.MessageFormat.format("products/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get();
    }

    public Response removeProduct(String id) throws ClientErrorException {
        Client client       = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI);
        WebTarget resource  = webTarget;
        resource = resource.path(java.text.MessageFormat.format("products/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).delete();
    }

    public <T> T list(Class<T> responseType) throws ClientErrorException {
        Client client       = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI);
        WebTarget resource  = webTarget;
        resource = resource.path("products");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response editProduct(Object requestEntity) throws ClientErrorException {
        Client client       = javax.ws.rs.client.ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI);
        return webTarget.path("product").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public void close(Client client) {
        client.close();
    }
    
}
