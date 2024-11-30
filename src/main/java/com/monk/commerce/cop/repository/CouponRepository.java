package com.monk.commerce.cop.repository;


import com.monk.commerce.cop.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
