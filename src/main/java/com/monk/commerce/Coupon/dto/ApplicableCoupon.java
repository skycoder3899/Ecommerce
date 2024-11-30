package com.monk.commerce.Coupon.dto;

import com.monk.commerce.Coupon.model.CouponType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicableCoupon{
    private  long coupon_id;
    private CouponType type;
    private double discount;
}
