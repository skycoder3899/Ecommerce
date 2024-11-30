package com.monk.commerce.cop.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.monk.commerce.cop.model.Coupon;
import com.monk.commerce.cop.model.CouponDetail;
import com.monk.commerce.cop.model.CouponType;
import com.monk.commerce.cop.model.detail.BxGyCoupon;
import com.monk.commerce.cop.model.detail.CartWiseCoupon;
import com.monk.commerce.cop.model.detail.ProductWiseCoupon;

import java.io.IOException;

public class CouponDeserializer extends JsonDeserializer<Coupon> {
    @Override
    public Coupon deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        JsonNode node = p.getCodec().readTree(p);
        String couponType = node.get("type").asText();
        Coupon coupon=new Coupon();
        coupon.setType(CouponType.fromType(couponType));
        coupon.setCouponDetail(getCouponDetail(p, couponType, node.get("details")));
        return coupon;
    }

    private static CouponDetail getCouponDetail(JsonParser p, String couponType, JsonNode node) throws IOException {
        return switch (couponType) {
            case "cart-wise" -> p.getCodec().treeToValue(node, CartWiseCoupon.class);
            case "product-wise" -> p.getCodec().treeToValue(node, ProductWiseCoupon.class);
            case "bxgy" -> p.getCodec().treeToValue(node, BxGyCoupon.class);
            default -> throw new IOException("Unknown coupon type: " + couponType);
        };
    }
}
