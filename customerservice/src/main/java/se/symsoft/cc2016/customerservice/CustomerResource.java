/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.customerservice;

import se.symsoft.cc2016.logutil.Logged;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

@Path("/customer")
public class CustomerResource {

    @GET
    public String test() {
        return "TEST";
    }

    @GET
    @Path("/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Logged
    public void getCustomerInfo(@Suspended final AsyncResponse asyncResponse, @PathParam("userName") String userName) throws URISyntaxException {
        new CustomerOps().getCustomerInfo(userName, asyncResponse);
    }
}
