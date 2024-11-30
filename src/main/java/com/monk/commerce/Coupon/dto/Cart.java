package com.monk.commerce.Coupon.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    @Valid
    @Size(min = 1, message = "Items must not be empty")
    private List<Item> items = new ArrayList<>();
    private double totalPrice;

    public double calculateTotalPrice() {
        if (items == null|| items.isEmpty()) {
            return 0.0;
        }
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public double getTotalPrice() {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }
        if (totalPrice == 0) {
            totalPrice = calculateTotalPrice();
        }
        return totalPrice;
    }

}

