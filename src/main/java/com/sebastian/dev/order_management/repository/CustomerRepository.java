package com.sebastian.dev.order_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sebastian.dev.order_management.repository.entity.CustomerEntity;

import java.util.List;



@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{
    List<CustomerEntity> findByAddress(String address);
}
