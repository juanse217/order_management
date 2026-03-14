package com.sebastian.dev.order_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sebastian.dev.order_management.repository.entity.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    Optional<ProductEntity> findBySku(String sku);
}
