package com.monk.commerce.cop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdatedCartDTO {
    private List<ItemDTO> items;
    @JsonProperty("total_price")
    private double totalPrice;
    @JsonProperty("total_discount")
    private double totalDiscount;
    @JsonProperty("final_price")
    private double finalPrice;
}

