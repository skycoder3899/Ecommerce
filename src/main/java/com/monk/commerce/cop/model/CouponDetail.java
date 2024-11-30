package com.monk.commerce.cop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.monk.commerce.cop.model.detail.BxGyCoupon;
import com.monk.commerce.cop.model.detail.CartWiseCoupon;
import com.monk.commerce.cop.model.detail.ProductWiseCoupon;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class CouponDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_seq")
    @SequenceGenerator(name = "coupon_seq", sequenceName = "coupon_sequence", allocationSize = 1)
    @JsonIgnore
    private Long id;
}
