package com.sebastian.dev.order_management.model;

import java.math.BigDecimal;

public class Product { //Order M:N Product
    private Long id;
    private final String sku; 
    private String name; 
    private BigDecimal price;
    
    private Product(String sku, String name, BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    private Product(String sku, String name, BigDecimal price, Long id){
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public static Product newInstance(String sku, String name, BigDecimal price){
        validateSku(sku);
        validateName(name);
        validatePrice(price);
        return new Product(sku, name, price);
    }

    public static Product reconstitute(String sku, String name, BigDecimal price, Long id){
        if (id == null || id <=0) {
            throw new IllegalArgumentException("id is required");
        }
        Product p = new Product(sku, name, price, id);

        return p; 
    }
    
    public Long getId() {
        return id;
    }
    
    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    } 

    public void updatePrice(BigDecimal newPrice){
        validatePrice(newPrice);
        this.price = newPrice;
    }

    public void updateName(String name){
        validateName(name);
        this.name = name; 
    }
    

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sku == null) ? 0 : sku.hashCode());
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
        Product other = (Product) obj;
        if (sku == null) {
            if (other.sku != null)
                return false;
        } else if (!sku.equals(other.sku))
            return false;
        return true;
    }

    //validation
    private static void validateSku(String sku){
        if(sku == null || sku.isBlank()){
            throw new IllegalArgumentException("The SKU is required");
        }
    }

    private static void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The name is required");
        }
    }

    private static void validatePrice(BigDecimal price){
        if(price == null || (price.compareTo(BigDecimal.ZERO) <=0)){
            throw new IllegalArgumentException("The price is required and has to be more than 0");
        }
    }
}
