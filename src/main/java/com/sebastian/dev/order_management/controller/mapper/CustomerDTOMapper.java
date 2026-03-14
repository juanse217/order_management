package com.sebastian.dev.order_management.controller.mapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.controller.dto.CustomerDTO;
import com.sebastian.dev.order_management.model.Customer;

@Component
public class CustomerDTOMapper {

    @NonNull
    @SuppressWarnings("null")
    public Customer toCustomer(@NonNull CustomerDTO dto, Long id) {
        Customer c;

        if(id == null){
            c = Customer.newInstance(dto.name(), dto.address());
        }else{
            c = Customer.reconstitute(id, dto.name(), dto.address());
        }
        
        return c;// non-null value returned by customer. 
    }

    @NonNull
    public CustomerDTO toCustomerDTO(@NonNull Customer c) {
        CustomerDTO dto = new CustomerDTO(c.getId(), c.getName(), c.getAddress());
        return dto;
    }
}
