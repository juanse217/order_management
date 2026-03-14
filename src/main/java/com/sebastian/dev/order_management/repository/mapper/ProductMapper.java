package com.sebastian.dev.order_management.repository.mapper;

import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sebastian.dev.order_management.model.Product;
import com.sebastian.dev.order_management.repository.entity.ProductEntity;


@Component
public class ProductMapper {

    @NonNull
    public Product toProduct(@NonNull ProductEntity entity){
        return Objects.requireNonNull(Product.reconstitute(entity.getSku(), entity.getName(), entity.getPrice(), entity.getId()));
    }

    @NonNull
    public ProductEntity toProductEntity(@NonNull Product product){

        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setSku(product.getSku());

        return entity;
    }
}
