package com.monk.commerce.Coupon.processor;

import com.monk.commerce.Coupon.dto.Cart;
import com.monk.commerce.Coupon.model.Coupon;

public interface CouponProcessor {
    boolean isApplicable(Cart cart, Coupon coupon);
    double calculateDiscount(Cart cart, Coupon coupon);
}