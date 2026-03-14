package com.sebastian.dev.order_management.controller.dto;

import java.math.BigDecimal;

public record OrderLineDTO(
    Long productId,
    String productName,
    String sku, 
    int quantity, 
    BigDecimal unitPrice
) {

}
