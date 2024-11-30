package com.monk.commerce.Coupon.repository;


import com.monk.commerce.Coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
