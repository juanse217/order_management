package com.sebastian.dev.order_management.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
/**
 * DTO for POST, GET and PUT operations.
 */
public record CustomerDTO(

    @Null(groups = OnPost.class, message = "The id must be null when creating a customer") //Used validation group for learning, not required. 
    Long id,

    @NotBlank(message = "The name is required")
    @Size(min = 5, max = 30, message = "The name must be between 5 and 30 characters")
    String name,
    
    @NotBlank(message = "The address is required")
    @Size(min = 5, max = 40, message = "The address must be between 5 and 40 characters")
    String address
) {
    public interface OnPost extends Default{}
}
