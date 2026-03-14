package com.sebastian.dev.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.dev.order_management.controller.dto.ProductDTO;
import com.sebastian.dev.order_management.controller.mapper.ProductDTOMapper;
import com.sebastian.dev.order_management.model.Product;
import com.sebastian.dev.order_management.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/products")
@Validated
@SuppressWarnings("null")
public class ProductController {

    private final ProductService service; 
    private final ProductDTOMapper mapper;

    public ProductController(ProductService service , ProductDTOMapper mapper){
        this.service = service;
        this.mapper = mapper; 
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(mapper.toProductDTO(service.findProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Validated(ProductDTO.OnPost.class)@RequestBody ProductDTO dto) {
        Product p = service.createProduct(mapper.toProduct(dto, null));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toProductDTO(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@NotNull @PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        Product p = service.updateProduct(mapper.toProduct(dto, id), id);
        return ResponseEntity.ok(mapper.toProductDTO(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@NotNull @PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
