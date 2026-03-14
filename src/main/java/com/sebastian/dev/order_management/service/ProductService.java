package com.sebastian.dev.order_management.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sebastian.dev.order_management.exception.ProductNotFoundException;
import com.sebastian.dev.order_management.model.Product;
import com.sebastian.dev.order_management.repository.ProductRepository;
import com.sebastian.dev.order_management.repository.entity.ProductEntity;
import com.sebastian.dev.order_management.repository.mapper.ProductMapper;

@Service
@Transactional
public class ProductService {
    private final ProductRepository pRepository;
    private final ProductMapper pMapper;

    public ProductService(ProductRepository pRepository, ProductMapper pMapper){
        this.pRepository = pRepository;
        this.pMapper = pMapper;
    }

    @Transactional(readOnly = true)
    public Product findProductById(@NonNull Long id){
        return pMapper.toProduct(findEntityById(id));
    }

    public Product createProduct(@NonNull Product p){
        return pMapper.toProduct(pRepository.save(pMapper.toProductEntity(p)));
    }

    /*
        This method only updates Name and Price, this is the expected domain model behavior. 
    */
    public Product updateProduct(@NonNull Product p,  @NonNull Long id){
        if(p.getId() == null){
            throw new IllegalArgumentException("The Product object id must be valid");
        }
        ProductEntity entity = findEntityById(id);

        entity.setName(p.getName());
        entity.setPrice(p.getPrice());

        pRepository.save(entity);
        return pMapper.toProduct(entity);
    }

    public void deleteProduct(@NonNull Long id){
        ProductEntity entity = findEntityById(id);
        pRepository.delete(entity);
    }

    //Helper methods
    @NonNull
    @SuppressWarnings("null")
    private ProductEntity findEntityById(@NonNull Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The id must be valid and greater than 0");
        }
        return pRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("The product with id " + id + " not found"));//the orElseThrow already guards against null. If changed, make sure to handle the null.
    }
}
