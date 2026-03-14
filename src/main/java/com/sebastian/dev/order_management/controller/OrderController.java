package com.sebastian.dev.order_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.dev.order_management.controller.dto.OrderRequestDTO;
import com.sebastian.dev.order_management.controller.dto.OrderResponseDTO;
import com.sebastian.dev.order_management.controller.mapper.OrderDTOMapper;
import com.sebastian.dev.order_management.model.Order;
import com.sebastian.dev.order_management.service.OrderService;

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



@RestController
@RequestMapping("/api/orders")
@Validated
@SuppressWarnings("null")
public class OrderController {
    private final OrderService service;
    private final OrderDTOMapper mapper;

    public OrderController(OrderService service, OrderDTOMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findOrderById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(mapper.toOrderResponseDTO(service.findOrderById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO dto) {
        Order o = service.createOrder(mapper.toOrder(dto), dto.productList());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toOrderResponseDTO(o));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@NotNull @PathVariable Long id){
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
