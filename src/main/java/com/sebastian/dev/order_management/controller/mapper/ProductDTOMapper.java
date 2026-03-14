package com.sebastian.dev.order_management.controller.mapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.controller.dto.ProductDTO;
import com.sebastian.dev.order_management.model.Product;

@Component
public class ProductDTOMapper {

    @NonNull
    @SuppressWarnings("null")
    public Product toProduct(@NonNull ProductDTO dto, Long id) {
        Product p;
        if(id == null){
            p = Product.newInstance(dto.sku(), dto.name(), dto.price());
        }else{
            p = Product.reconstitute(dto.sku(), dto.name(), dto.price(), id);
        }

        return p; // Product.newInstance/reconstitute ensures a non-null value
    }

    @NonNull
    public ProductDTO toProductDTO(@NonNull Product p) {
        ProductDTO dto = new ProductDTO(p.getId(), p.getSku(), p.getName(), p.getPrice());
        return dto;
    }
}
