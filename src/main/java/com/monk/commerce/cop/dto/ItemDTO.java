package com.monk.commerce.cop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {
    @JsonProperty("product_id")
    private Long productId;
    private int quantity;
    private double price;
    private double totalDiscount;
}

