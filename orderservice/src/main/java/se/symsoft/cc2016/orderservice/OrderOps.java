/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.orderservice;

import se.symsoft.cc2016.orderservice.customerapi.CustomerApi;
import se.symsoft.cc2016.orderservice.customerapi.CustomerData;
import se.symsoft.cc2016.orderservice.customerapi.CustomerInfoCallback;

import javax.ws.rs.container.AsyncResponse;
import java.net.URISyntaxException;
import java.util.UUID;

public class OrderOps {

    public void handleOrderRequest(String userName, OrderRequest order, final AsyncResponse asyncResponse) throws URISyntaxException {
        CustomerApi.getInstance().getCustomerInfo(userName, new CustomerInfoCallbackWorker(order, asyncResponse));
    }



    private class CustomerInfoCallbackWorker implements CustomerInfoCallback {
        private final OrderRequest orderRequest;
        private final AsyncResponse asyncResponse;

        public CustomerInfoCallbackWorker(OrderRequest orderRequest, AsyncResponse asyncResponse) {
            this.orderRequest = orderRequest;
            this.asyncResponse = asyncResponse;
        }

        @Override
        public void completed(CustomerData customerData) {
            OrderData order = new OrderData(UUID.randomUUID(), orderRequest.getProductId(),
                    orderRequest.getQuantity(), customerData.getId());
            sendOrderToInventory(order);
            asyncResponse.resume(new OrderResponse(order.getId()));
        }



        @Override
        public void failed(Throwable throwable) {
            throwable.printStackTrace();
            asyncResponse.resume(throwable);
        }

        private void sendOrderToInventory(OrderData order) {
        }
    }
}
