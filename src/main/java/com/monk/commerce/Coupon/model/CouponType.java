package com.monk.commerce.Coupon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CouponType {
    CART_WISE("cart-wise"),
    PRODUCT_WISE("product-wise"),
    BXGY("bxgy");
    private final String type;

    CouponType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static CouponType fromType(String type) {
        for (CouponType couponType : CouponType.values()) {
            if (couponType.type.equalsIgnoreCase(type)) {
                return couponType;
            }
        }
        throw new IllegalArgumentException("Unknown CouponType: " + type);
    }
}
