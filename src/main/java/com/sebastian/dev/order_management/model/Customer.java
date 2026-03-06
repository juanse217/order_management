package com.sebastian.dev.order_management.model;

import java.util.List;

public class Customer { // Customer 1:M Order
    private Long id; 
    private String name; 
    private String address; 

    private Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    private Customer(Long id, String name, String address) {
        this.id = id;  
        this.name = name;
        this.address = address;
    }

    public static Customer newInstance(String name, String address){
        validateName(name);
        validateAddress(address);
        return new Customer(name, address);
    }


    public static Customer reconstitute(Long id, String name, String address, List<Long> orders){
        if (id == null || id <=0) {
            throw new IllegalArgumentException("id is required");
        }
        if(orders == null){
            throw new IllegalArgumentException("The order list is required");
        }

        Customer c = new Customer(id, name, address);
        return c; 
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void updateName(String newName){
        validateName(newName);
        this.name = newName;
    }
    public void updateAddress(String newAddress){
        validateAddress(newAddress);
        this.address = newAddress;
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
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    //Helper validation methods

    private static void validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("The name is required");
        }
    }

    private static void validateAddress(String address){
        if(address == null || address.isBlank()){
            throw new IllegalArgumentException("The address is required");
        }
    }
}
