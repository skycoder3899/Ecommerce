package com.monk.commerce.Coupon.processor;

import com.monk.commerce.Coupon.dto.Cart;
import com.monk.commerce.Coupon.model.Coupon;
import com.monk.commerce.Coupon.model.detail.ProductWiseCoupon;
import org.springframework.stereotype.Component;

@Component
public class ProductWiseProcessor implements CouponProcessor {
    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        ProductWiseCoupon details = (ProductWiseCoupon) coupon.getCouponDetail();
        return cart.getItems().stream().anyMatch(item -> item.getProductId()==details.getProductId());
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        ProductWiseCoupon details = (ProductWiseCoupon) coupon.getCouponDetail();
        return cart.getItems().stream()
                .filter(item -> item.getProductId()==details.getProductId())
                .mapToDouble(item -> item.getPrice() * item.getQuantity() * (details.getDiscount() / 100))
                .sum();
    }
}

