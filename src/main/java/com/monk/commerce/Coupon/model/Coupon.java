package com.monk.commerce.Coupon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.monk.commerce.Coupon.utility.CouponDeserializer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@JsonDeserialize(using = CouponDeserializer.class)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("coupon_id")
    private Long id;

    @JsonProperty(value = "type",required = true)
    @NotNull(message = "Coupon type must not be null")
    private CouponType type;

    @NotNull(message = "Details must not be null")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_detail_id")
    @JsonProperty(value = "details",required = true)
    @Valid
    private CouponDetail couponDetail;
}
