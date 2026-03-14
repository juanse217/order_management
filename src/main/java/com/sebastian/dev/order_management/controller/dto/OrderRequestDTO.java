package com.sebastian.dev.order_management.controller.dto;

import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
/***
 * Used for POST.
 */
public record OrderRequestDTO(

    @NotNull(message = "The date is required")
    @FutureOrPresent(message = "The date must be in the present or future")
    LocalDate orderDate,

    @NotNull(message = "The customer id is required")
    @Positive(message = "Not negative ids allowed")
    Long customerId,

    @NotNull(message = "The product ids list and quantities is required")
    @Size(min = 1, message = "You must at least add 1 product. ")
    Map<Long, Integer> productList
) {

}
