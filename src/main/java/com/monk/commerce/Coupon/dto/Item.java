package com.monk.commerce.Coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("product_id")
    @Positive(message = "Product Id is Missing")
    private int productId;
    @Positive(message = "Quantity must not be null")
    private int quantity;
    @Positive(message = "Price must not be null")
    private double price;
}

