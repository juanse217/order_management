package com.sebastian.dev.order_management.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Order { // Order M:N Product
    private Long id;
    private final LocalDate orderDate;
    private final List<OrderLine> orderProducts = new ArrayList<>(); // Ensures the list reference cannot be changed.
    private final Long customerId;// Ensures the customer cannot be changed.

    private Order(LocalDate orderDate, Long customerId) {
        this.orderDate = orderDate;
        this.customerId = customerId;
    }

    private Order(LocalDate orderDate, Long customerId, Long id) {
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.id = id;
    }

    public static Order newInstance(LocalDate date, Long c) {
        validateDate(date);
        validateCreationDate(date);
        validateCustomer(c);
        return new Order(date, c);
    }

    public static Order reconstitute(LocalDate date, Long customerId, List<OrderLine> products, Long id) {
        validateDate(date);
        validateCustomer(customerId);
        if (products == null) {
            throw new IllegalArgumentException("The products list cannot be null");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id is required");
        }
        Order o = new Order(date, customerId, id);
        o.orderProducts.addAll(products);
        return o;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Collection<OrderLine> getOrderProducts() {
        return Collections.unmodifiableCollection(orderProducts);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void addProduct(Product product, int quantity) {
        OrderLine line = new OrderLine(product.getId(), product.getName(), product.getSku(), quantity,
                product.getPrice());
        orderProducts.add(line);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // validation methods
    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("The date is required.");
        }
    }

    private static void validateCreationDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The date cannot be in the past");
        }
    }

    private static void validateCustomer(Long c) {
        if (c == null || c <= 0) { // Id is checked by Customer class.
            throw new IllegalArgumentException("The customer is required");
        }
    }
}
