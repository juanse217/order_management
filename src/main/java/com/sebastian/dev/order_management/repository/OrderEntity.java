package com.sebastian.dev.order_management.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders", schema = "order_management")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "orderObject", cascade = CascadeType.ALL, orphanRemoval = true)//OrderLine is a Value object added to Order.It has no meaning without an order, this class manages the lifecycle of orderLine. Product does not. We can delete a product but still keep the record. If an order is deleted, we delete the tightly coupled object. 
    private List<OrderLineEntity> orderProducts = new ArrayList<>(); //Works for our M:M relationship with Products. Like an intermediate table. 

    @ManyToOne(fetch = FetchType.LAZY) //improved performance, no need to load the parent always when calling a child. 
    @JoinColumn(name = "customer_id")//FK column
    private CustomerEntity customer;

    public OrderEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLineEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderLineEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    
}
