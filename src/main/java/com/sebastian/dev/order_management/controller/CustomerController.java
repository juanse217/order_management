package com.sebastian.dev.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.dev.order_management.controller.dto.CustomerDTO;
import com.sebastian.dev.order_management.controller.mapper.CustomerDTOMapper;
import com.sebastian.dev.order_management.model.Customer;
import com.sebastian.dev.order_management.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/customers")
@Validated//triggers validation for method parameters.
@SuppressWarnings("null") //We have Java Bean Validation to ensure valid and non-null elements 
public class CustomerController {

    private final CustomerService service;
    private final CustomerDTOMapper mapper;

    public CustomerController(CustomerService service, CustomerDTOMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    
    public ResponseEntity<CustomerDTO> findCustomerById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(mapper.toCustomerDTO(service.findCustomerById(id))); //requires non-null. Already checked by jakarta. 
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Validated(CustomerDTO.OnPost.class) @RequestBody CustomerDTO dto) {
        Customer c = service.createCustomer(mapper.toCustomer(dto, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCustomerDTO(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@NotNull @PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        Customer c = service.updateCustomer(mapper.toCustomer(dto, id), id);
        return ResponseEntity.ok(mapper.toCustomerDTO(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@NotNull @PathVariable Long id){
        service.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }    
}
