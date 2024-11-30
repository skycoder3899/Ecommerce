package com.monk.commerce.cop.processor;

import com.monk.commerce.cop.dto.Cart;
import com.monk.commerce.cop.model.Coupon;
import com.monk.commerce.cop.dto.Item;
import com.monk.commerce.cop.model.detail.BxGyCoupon;
import com.monk.commerce.cop.model.detail.ProductDetail;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BxGyProcessor implements CouponProcessor {

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        if (!(coupon.getCouponDetail() instanceof BxGyCoupon)) {
            return false;
        }
        BxGyCoupon bxGyCoupon = (BxGyCoupon) coupon.getCouponDetail();

        Map<Integer, Integer> cartProductQuantity = cart.getItems().stream()
                .collect(Collectors.toMap(
                        Item::getProductId,
                        Item::getQuantity,
                        Integer::sum
                ));

        for (ProductDetail buyProduct : bxGyCoupon.getBuyProducts()) {
            int requiredQty = buyProduct.getQuantity();
            int availableQty = cartProductQuantity.getOrDefault(buyProduct.getProductId(), 0);
            if (availableQty < requiredQty) {
                return false;
            }
        }

        return true;
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        if (!(coupon.getCouponDetail() instanceof BxGyCoupon)) {
            return 0;
        }

        BxGyCoupon bxGyCoupon = (BxGyCoupon) coupon.getCouponDetail();

        Map<Integer, Integer> cartProductQuantity = cart.getItems().stream()
                .collect(Collectors.toMap(
                        Item::getProductId,
                        Item::getQuantity,
                        Integer::sum
                ));

        int repetitionCount = bxGyCoupon.getRepitionLimit();

        for (ProductDetail buyProduct : bxGyCoupon.getBuyProducts()) {
            int requiredQty = buyProduct.getQuantity();
            int availableQty = cartProductQuantity.getOrDefault(buyProduct.getProductId(), 0);

            int possibleReps = availableQty / requiredQty;
            repetitionCount = Math.max(repetitionCount, possibleReps);
        }

        if (repetitionCount == 0) {
            return 0;
        }
        repetitionCount = Math.min(repetitionCount, bxGyCoupon.getRepitionLimit());

        double discount = 0;
        for (ProductDetail getProduct : bxGyCoupon.getGetProducts()) {
            int freeQty = getProduct.getQuantity() * repetitionCount;
            double productPrice = cart.getItems().stream()
                    .filter(item -> item.getProductId()==getProduct.getProductId())
                    .mapToDouble(Item::getPrice)
                    .findFirst()
                    .orElse(0.0);

            discount += freeQty * productPrice;
        }

        return discount;
    }
}
