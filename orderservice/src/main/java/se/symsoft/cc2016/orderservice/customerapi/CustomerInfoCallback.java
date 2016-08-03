/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice.customerapi;

public interface CustomerInfoCallback {

    public void completed(CustomerData customerData);
    public void failed(Throwable throwable);
}
