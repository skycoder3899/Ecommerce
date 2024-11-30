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
public class CartWiseCoupon extends CouponDetail {

    @Positive(message = "Threshold must be greater than zero")
    @JsonProperty(required = true)
    private double threshold;

    @Positive(message = "Discount must be greater than zero")
    @JsonProperty(required = true)
    private double discount;
}
