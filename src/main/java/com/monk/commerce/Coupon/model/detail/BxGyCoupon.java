package com.monk.commerce.Coupon.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monk.commerce.Coupon.model.CouponDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class BxGyCoupon extends CouponDetail {
    @Valid
    @Size(min = 1, message = "At least one product must be specified to buy")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bxgy_coupon_id")
    @NotNull(message = "Buy Products can not be null")
    @JsonProperty(value = "buy_products",required = true)
    private List<ProductDetail> buyProducts;

    @Valid
    @JsonProperty(value = "get_products",required = true)
    @Size(min = 1, message = "At least one product must be specified to get")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bxgy_coupon_get_id")
    @NotNull(message = "Get Free Products can not be null")
    private List<ProductDetail> getProducts;

    @JsonProperty(value = "repition_limit",required = true)
    @Min(value = 1, message = "Repetition limit must be at least 1")
    @NotNull(message = "Repetition limit must not be null")
    private  int repitionLimit;
}
