package com.sebastian.dev.order_management.model;
/**
 * This class  represents a product, its price and the quantity of that item bought in the Order. 
 */

import java.math.BigDecimal;

public class OrderLine { //Value object
    private final Long productId; //Pragmatic approach for mapping strategy. Avoid complex and repetitive logic in service. 
    private final String productName;
    private final String sku; 
    private final int quantity; 
    private final BigDecimal unitPrice; //price at the time of purchase
    
    public OrderLine(Long productId, String productName, String sku, int quantity, BigDecimal unitPrice) {
        validateName(productName);
        validateSku(sku);
        validateQuantity(quantity);
        validatePrice(unitPrice);
        validateId(productId);
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getTotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    //validation methods
    private static void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The product name is required");
        }
    }

    private static void validateSku(String sku){
        if(sku == null || sku.isBlank()){
            throw new IllegalArgumentException("The product sku is required");
        }
    }
    private static void validateQuantity(int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("You have to add at least 1 product");
        }
    }
    //Method duplicated in Product.validatePrice(). We take the minor duplication since it's just one duplication. IN the case we had more duplications we could start thinking about a validator class for our Domain.
    private static void validatePrice(BigDecimal price){
        if(price == null || (price.compareTo(BigDecimal.ZERO) <=0)){
            throw new IllegalArgumentException("The price is required and has to be more than 0");
        }
    }

    private static void validateId(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The product id must be valid");
        }
    }
}
