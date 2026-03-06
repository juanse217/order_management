package com.sebastian.dev.order_management.repository;

import java.io.Serializable;
import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_products", schema = "order_management")
@IdClass(OrderLineEntity.OrderLineId.class)
public class OrderLineEntity {//Intermediate table. 
    
    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product; 

    @Column(name = "product_name", nullable = false, length = 40)
    private String productName;

    @Column(nullable = false, length = 10)
    private String sku; 

    @Column(nullable = false)
    private int quantity; 

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice; 

    public OrderLineEntity(){}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderEntity getOrderObject() {
        return orderObject;
    }

    public void setOrderObject(OrderEntity orderObject) {
        this.orderObject = orderObject;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static class  OrderLineId implements Serializable {
        
        private Long orderId;
        private Long productId;

        public Long getOrderId() {
            return orderId;
        }
        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }
        public Long getProductId() {
            return productId;
        }
        public void setProductId(Long productId) {
            this.productId = productId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
            result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
            OrderLineId other = (OrderLineId) obj;
            if (orderId == null) {
                if (other.orderId != null)
                    return false;
            } else if (!orderId.equals(other.orderId))
                return false;
            if (productId == null) {
                if (other.productId != null)
                    return false;
            } else if (!productId.equals(other.productId))
                return false;
            return true;
        } 

        
        
    }

}
