/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice;

import java.util.UUID;

public class OrderData {
    private final UUID id;
    private final int productId;
    private final int quantity;
    private final UUID customerId;

    public OrderData(UUID id, int productId, int quantity, UUID customerId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    public UUID getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
