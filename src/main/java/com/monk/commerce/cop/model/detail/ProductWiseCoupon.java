package com.monk.commerce.cop.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monk.commerce.cop.model.CouponDetail;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ProductWiseCoupon extends CouponDetail {
    @JsonProperty("product_id")
    private int productId;
    private double discount;
}
