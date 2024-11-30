package com.monk.commerce.Coupon.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(value = "product_id",required = true)
    @Positive(message = "Product Id is missing")
    private int productId;

    @JsonProperty(required = true)
    @Positive(message = "Quantity must be greater than zero")
    private int quantity;
}
