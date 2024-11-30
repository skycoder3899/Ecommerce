package com.monk.commerce.Coupon.processor;

import com.monk.commerce.Coupon.dto.Cart;
import com.monk.commerce.Coupon.model.Coupon;
import com.monk.commerce.Coupon.dto.Item;
import com.monk.commerce.Coupon.model.detail.BxGyCoupon;
import com.monk.commerce.Coupon.model.detail.ProductDetail;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BxGyProcessor implements CouponProcessor {

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        if (!(coupon.getCouponDetail() instanceof BxGyCoupon bxGyCoupon)) {
            return false;
        }

        Map<Integer, Integer> cartProductQuantity = cart.getItems().stream()
                .collect(Collectors.toMap(
                        Item::getProductId,
                        Item::getQuantity,
                        Integer::sum
                ));

        for (ProductDetail buyProduct : bxGyCoupon.getBuyProducts()) {
            int requiredQty = buyProduct.getQuantity();
            int availableQty = cartProductQuantity.getOrDefault(buyProduct.getProductId(), 0);
            if (availableQty >= requiredQty) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double calculateDiscount(Cart cart, Coupon coupon) {
        if (!(coupon.getCouponDetail() instanceof BxGyCoupon bxGyCoupon)) {
            return 0;
        }

        Map<Integer, Integer> cartProductQuantity = cart.getItems().stream()
                .collect(Collectors.toMap(
                        Item::getProductId,
                        Item::getQuantity,
                        Integer::sum
                ));


        int maxBuyApplications = bxGyCoupon.getBuyProducts().stream()
                .mapToInt(buy -> cartProductQuantity.getOrDefault(buy.getProductId(), 0) / buy.getQuantity())
                .sum();

        if (maxBuyApplications == 0) {
            return 0;
        }
        maxBuyApplications = Math.min(maxBuyApplications, bxGyCoupon.getRepitionLimit());

        double discount = 0;
        for (ProductDetail getProduct : bxGyCoupon.getGetProducts()) {
            int freeQty = getProduct.getQuantity() * maxBuyApplications;
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
