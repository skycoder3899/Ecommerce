package com.monk.commerce.Coupon.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.monk.commerce.Coupon.model.Coupon;
import com.monk.commerce.Coupon.model.CouponDetail;
import com.monk.commerce.Coupon.model.CouponType;
import com.monk.commerce.Coupon.model.detail.BxGyCoupon;
import com.monk.commerce.Coupon.model.detail.CartWiseCoupon;
import com.monk.commerce.Coupon.model.detail.ProductWiseCoupon;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CouponDeserializer extends JsonDeserializer<Coupon> {

    @Override
    public Coupon deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode typeNode = node.get("type");
        if (typeNode == null || typeNode.asText().isEmpty()) {
            throw new JsonMappingException(p, "Coupon type is required but was not provided in the request payload");
        }

        String couponType = typeNode.asText();
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
