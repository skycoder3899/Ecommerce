package com.monk.commerce.cop.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monk.commerce.cop.model.CouponDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class BxGyCoupon extends CouponDetail {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bxgy_coupon_id")
    @JsonProperty("buy_products")
    private List<ProductDetail> buyProducts;

    @JsonProperty("get_products")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bxgy_coupon_get_id")
    private List<ProductDetail> getProducts;

    @JsonProperty("repition_limit")
    private  int repitionLimit;
}
