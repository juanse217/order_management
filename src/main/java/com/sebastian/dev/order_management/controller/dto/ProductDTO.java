package com.sebastian.dev.order_management.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

/**
 * Used for GET, POST and PUT operations. We allow the null id for this purpose
 * as the exposed information is almost the same as the one received.
 */
public record ProductDTO(
        @Null(groups = OnPost.class, message = "Product id must be null when creating a new product") Long id,

        @NotBlank(message = "The SKU is required") @Size(min = 3, max = 10, message = "The SKU must be between 3 and 10 characters") String sku,

        @NotBlank(message = "The Product name is required") @Size(min = 5, max = 40, message = "The name must be between 5 and 40 characters") String name,
        @Positive @Digits(integer = 10, fraction = 2, message = "The price can't have more than 10 digits (including 2 fractional)") BigDecimal price //TODO: possible refactor, convert to LONG and use mapper to convert to BigDecimal.
    ) {
    public interface OnPost extends Default {
    }
}
