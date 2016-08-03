/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import se.symsoft.cc2016.orderservice.logging.RequestLoggingFilter;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderService {

    static HttpServer startServer() throws IOException {


        URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9998).build();
        ResourceConfig config = new ResourceConfig(OrderResource.class);
        config.register(RequestLoggingFilter.class);
        config.register(JacksonJsonProvider.class);


        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();
        return  server;
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
