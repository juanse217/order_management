package com.sebastian.dev.order_management.service;



import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.dev.order_management.exception.CustomerNotFoundException;
import com.sebastian.dev.order_management.exception.OrderNotFoundException;
import com.sebastian.dev.order_management.exception.ProductNotFoundException;
import com.sebastian.dev.order_management.model.Order;
import com.sebastian.dev.order_management.model.Product;
import com.sebastian.dev.order_management.repository.CustomerRepository;
import com.sebastian.dev.order_management.repository.OrderRepository;
import com.sebastian.dev.order_management.repository.ProductRepository;
import com.sebastian.dev.order_management.repository.entity.CustomerEntity;
import com.sebastian.dev.order_management.repository.entity.OrderEntity;
import com.sebastian.dev.order_management.repository.mapper.OrderMapper;
import com.sebastian.dev.order_management.repository.mapper.ProductMapper;



@Service
@Transactional
public class OrderService {

    private final OrderRepository oRepository;
    private final CustomerRepository cRepository;
    private final ProductRepository pRepository;
    private final OrderMapper oMapper;
    private final ProductMapper pMapper;

    public OrderService(OrderRepository oRepository, CustomerRepository cRepository, ProductRepository pRepository, OrderMapper oMapper, ProductMapper pMapper) {
        this.oRepository = oRepository;
        this.cRepository = cRepository;
        this.pRepository = pRepository;
        this.oMapper = oMapper;
        this.pMapper = pMapper;
    }

    @Transactional(readOnly = true)
    public Order findOrderById(@NonNull Long id){
        return oMapper.toOrder(findOrderEntityById(id));
    }

    
    public Order createOrder(@NonNull Order order, @NonNull Map<Long, Integer> productList){

        CustomerEntity cEntity = findCustomerEntity(Objects.requireNonNull(order.getCustomerId(), "The customer id must be valid"));
        List<Product> products = findProducts(productList.keySet());

        if(products.size() != productList.keySet().size()){
            throw new ProductNotFoundException("One of the products passed was not found");
        }

        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);
            order.addProduct(currentProduct, productList.get(currentProduct.getId()));
        }

        OrderEntity orderEntity = oMapper.toOrderEntity(order, cEntity);
        return oMapper.toOrder(oRepository.save(orderEntity));
    }

    public void deleteOrder(@NonNull Long id){
        OrderEntity entity = findOrderEntityById(id);
        oRepository.delete(entity);
    }

    //Helper methods
    @NonNull
    @SuppressWarnings("null")
    private OrderEntity findOrderEntityById(@NonNull Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The Id must be valid and greater than 0");
        }

        return oRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("The order with id " + id + " not found"));
        //the orElseThrow already guards against null. If changed, make sure to handle the null.
    }

    private CustomerEntity findCustomerEntity(@NonNull Long customerId){
        if(customerId == null || customerId <= 0){
            throw new IllegalArgumentException("The id must be valid and greater than 0");
        }
        return cRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("The customer with id " + customerId + " not found for creating order"));
    }

    private List<Product> findProducts(Set<Long> productIds){
        //Negative ids won't be in the DB, just checking null. The count checker in createOrder will complete the flow. 
        if(productIds.contains(null)){
            throw new IllegalArgumentException("The ids required for order creation must be valid");
        }
        return pRepository.findAllById(Objects.requireNonNull(productIds)).stream().map(
            pMapper::toProduct
        ).collect(Collectors.toList());
    }
}
