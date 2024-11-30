package com.monk.commerce.Coupon.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monk.commerce.Coupon.model.CouponDetail;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ProductWiseCoupon extends CouponDetail {
    @JsonProperty("product_id")
    @Positive(message = "Product Id is missing")
    private int productId;

    @Positive(message = "Discount must be greater than zero")
    private double discount;
}
