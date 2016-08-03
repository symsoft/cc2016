/*
 * Copyright Symsoft AB 1996-2015. All Rights Reserved.
 */
package se.symsoft.cc2016.customerservice;

import javax.ws.rs.container.AsyncResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CustomerOps {
    private static Map<String, CustomerData> customerByNameMap = new HashMap<>();
    private static Map<UUID, CustomerData> customerByIdMap = new HashMap<>();

    static {
        CustomerData kalle = new CustomerData(UUID.randomUUID(), "Kalle");
        CustomerData pelle = new CustomerData(UUID.randomUUID(), "Pelle");
        customerByIdMap.put(kalle.getId(), kalle);
        customerByIdMap.put(pelle.getId(), pelle);
        customerByNameMap.put(kalle.getUserName(), kalle);
        customerByNameMap.put(pelle.getUserName(), pelle);


    }


    public void getCustomerInfo(final String userName, AsyncResponse asyncResponse) {
        CustomerService.threadPool.execute(() -> {
            CustomerData customer = null;
            customer = customerByNameMap.get(userName);
            if (customer == null) {
                customer = customerByIdMap.get(userName);
                if (customer == null) {
                    System.out.println("Customer not found");
                    asyncResponse.resume(new IllegalArgumentException("Customer with userName or Id="+userName+" not found"));
                    return;
                }
            }
            asyncResponse.resume(customer);
        });
    }
}
