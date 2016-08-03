/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice.customerapi;

import java.util.UUID;

public class CustomerData {
    private UUID id;
    private String userName;

    public CustomerData(UUID id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    /**
     * Needed for JAX-RS unmarshalling
     */
    public CustomerData() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
