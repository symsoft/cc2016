/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.customerservice;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import se.symsoft.cc2016.logutil.RequestLoggingFilter;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerService {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    static HttpServer startServer() throws IOException {
        URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9997).build();
        ResourceConfig config = new ResourceConfig(CustomerResource.class);
        config.register(RequestLoggingFilter.class);
        config.register(JacksonJsonProvider.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();
        return server;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> {
            try {
                startServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


    }

}
