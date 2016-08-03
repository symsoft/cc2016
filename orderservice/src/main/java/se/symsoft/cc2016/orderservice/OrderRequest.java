/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice;

public class OrderRequest {
    private int productId;
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quanity) {
        this.quantity = quanity;
    }
}
