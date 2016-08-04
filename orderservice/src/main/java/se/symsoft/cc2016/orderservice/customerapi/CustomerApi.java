/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice.customerapi;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;

public class CustomerApi {
    private static final String CUSTOMER_SERVICE_URL = "http://customer.service.consul:8090";
    private static final String CUSTOMER_PATH = "customer";

    private static CustomerApi instance;
    private final URI uri;
    private final Client client;

    public CustomerApi() throws URISyntaxException {
        this.uri = new URI(CUSTOMER_SERVICE_URL);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonJsonProvider.class);
        this.client = ClientBuilder.newClient(clientConfig);
    }

    public static CustomerApi getInstance() throws URISyntaxException {
        if (instance == null) {
            instance = new CustomerApi();
        }
        return instance;
    }

    public WebTarget createNewWebTarget() {
        return client.target(uri);
    }

    public void getCustomerInfo(String userName, CustomerInfoCallback callback) {

        WebTarget webTarget = createNewWebTarget().path(CUSTOMER_PATH + "/"+userName);
        webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().submit(new InvocationCallback<CustomerData>() {
            public void completed(CustomerData customerData) {
                callback.completed(customerData);
            }

            public void failed(Throwable throwable) {
                callback.failed(throwable);
            }
        });

    }
}
