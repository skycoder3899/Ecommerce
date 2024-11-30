package com.monk.commerce.Coupon.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CartWrapper(@NotNull(message = "Cart must not be null") @Valid Cart cart) {
}