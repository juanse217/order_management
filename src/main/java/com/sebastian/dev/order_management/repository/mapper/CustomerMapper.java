package com.sebastian.dev.order_management.repository.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.model.Customer;
import com.sebastian.dev.order_management.repository.entity.CustomerEntity;

@Component
public class CustomerMapper{

    /**
     * maps from CustomerEntity to Customer
     * @param customers the list containing the CustomerEntity objects 
     * @return a list consisting of Customer objects
     */
    public List<Customer> toCustomerList(List<CustomerEntity> customers){
        if(customers == null){
            throw new IllegalArgumentException("Required valid CustomerEntity list for mapping");
        }
        return customers.stream().map(
            this::toCustomer
        ).collect(Collectors.toList());
    }

    @NonNull
    public Customer toCustomer(@NonNull CustomerEntity customer){
        return Objects.requireNonNull(Customer.reconstitute(customer.getId(), customer.getName(), customer.getAddress()), "Customer.reconstitue must not return null");
    }

    @NonNull
    public CustomerEntity toCustomerEntity(@NonNull Customer customer){
        CustomerEntity c = new CustomerEntity();
        c.setId(customer.getId());
        c.setName(customer.getName());
        c.setAddress(customer.getAddress());

        return c; 
    }
}
