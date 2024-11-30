package com.monk.commerce.Coupon.processor;

import com.monk.commerce.Coupon.dto.Cart;
import com.monk.commerce.Coupon.model.Coupon;
import com.monk.commerce.Coupon.model.detail.CartWiseCoupon;
import org.springframework.stereotype.Component;

@Component
public class CartWiseProcessor implements CouponProcessor {
    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        CartWiseCoupon details = (CartWiseCoupon) coupon.getCouponDetail();
        return cart.getTotalPrice() > details.getThreshold();
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        CartWiseCoupon details = (CartWiseCoupon) coupon.getCouponDetail();
        return cart.getTotalPrice() * (details.getDiscount() / 100);
    }
}

