package com.monk.commerce.cop.processor;

import com.monk.commerce.cop.dto.Cart;
import com.monk.commerce.cop.model.Coupon;

public interface CouponProcessor {
    boolean isApplicable(Cart cart, Coupon coupon);
    double calculateDiscount(Cart cart, Coupon coupon);
}