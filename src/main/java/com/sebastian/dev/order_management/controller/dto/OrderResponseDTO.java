package com.sebastian.dev.order_management.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDTO(

        Long id,
        LocalDate orderDate,
        Long customerId,
        List<OrderLineDTO> products

) {

}
