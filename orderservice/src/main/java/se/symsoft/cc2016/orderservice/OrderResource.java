/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ContainerRequest;
import se.symsoft.cc2016.orderservice.logging.Logged;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;
import java.util.List;

@Path("order")
public class OrderResource {

    @Context
    ContainerRequest containerRequest;

    @GET
    public String test() {
        return "TEST";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Logged
    public void create(@Suspended final AsyncResponse asyncResponse, OrderRequest order) throws URISyntaxException {
        String auth = containerRequest.getHeaderString("Authorization");
        auth = auth.replaceFirst("[Bb]asic ", "");
        String userColonPass = Base64.decodeAsString(auth);
        String user = userColonPass.substring(0, userColonPass.indexOf(":"));
        new OrderOps().handleOrderRequest(user, order, asyncResponse);
    }

}
