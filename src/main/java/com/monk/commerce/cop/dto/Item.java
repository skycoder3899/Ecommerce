package com.monk.commerce.cop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("product_id")
    @NotNull(message = "Product Id must not be null")
    private int productId;
    @NotNull(message = "Quantity must not be null")
    private int quantity;
    @NotNull(message = "Price must not be null")
    private double price;
}

