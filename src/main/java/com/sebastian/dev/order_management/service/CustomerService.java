package com.sebastian.dev.order_management.service;



import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.dev.order_management.exception.CustomerNotFoundException;
import com.sebastian.dev.order_management.model.Customer;
import com.sebastian.dev.order_management.repository.CustomerRepository;
import com.sebastian.dev.order_management.repository.entity.CustomerEntity;
import com.sebastian.dev.order_management.repository.mapper.CustomerMapper;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository cRepository;
    private final CustomerMapper cMapper;
    
    public CustomerService(CustomerRepository cRepository, CustomerMapper cMapper){
        this.cRepository = cRepository;
        this.cMapper = cMapper;
    }

    @Transactional(readOnly = true)
    public Customer findCustomerById(@NonNull Long id){
        CustomerEntity entity = findEntityById(id);
        return cMapper.toCustomer(entity);
    }

    public Customer createCustomer(@NonNull Customer c){
        return cMapper.toCustomer(cRepository.save(cMapper.toCustomerEntity(c)));
    }

    public Customer updateCustomer(@NonNull Customer c, @NonNull Long id){
        if(c.getId() == null){
            throw new IllegalArgumentException("The customer object id must be valid");
        }
        
        CustomerEntity foundEntity = findEntityById(id);
        foundEntity.setName(c.getName());
        foundEntity.setAddress(c.getAddress());

        cRepository.save(foundEntity);

        return cMapper.toCustomer(foundEntity);
    }

    public void deleteCustomer(@NonNull Long id){
        cRepository.delete(findEntityById(id));
    }
    //helper methods
    @NonNull
    @SuppressWarnings("null")
    private CustomerEntity findEntityById(@NonNull Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The id must be valid and greater than 0");
        }
        return cRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("The customer with id " + id + " not found")); //the orElseThrow already guards against null. If ever changed, make sure to handle the null.  
    }
}
