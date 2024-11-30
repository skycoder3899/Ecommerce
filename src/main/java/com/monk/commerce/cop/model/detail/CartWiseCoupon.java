package com.monk.commerce.cop.model.detail;

import com.monk.commerce.cop.model.CouponDetail;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CartWiseCoupon extends CouponDetail {
    private double threshold;
    private double discount;
}
