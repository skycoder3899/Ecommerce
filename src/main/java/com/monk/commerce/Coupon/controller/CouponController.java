package com.monk.commerce.Coupon.controller;

import com.monk.commerce.Coupon.dto.ApplicableCoupon;
import com.monk.commerce.Coupon.dto.CartWrapper;
import com.monk.commerce.Coupon.dto.UpdatedCartDTO;
import com.monk.commerce.Coupon.model.*;
import com.monk.commerce.Coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        log.info("Request to get all coupons received.");
        List<Coupon> coupons = couponService.getAllCoupon();
        log.info("Found {} coupons.", coupons.size());
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        log.info("Fetching coupon with ID: {}", id);
        Coupon coupon = couponService.getCouponById(id);
        log.info("Found {} coupons.", coupon);
        return ResponseEntity.ok(coupon);
    }

    @PutMapping(value = "/coupons/{id}", consumes = "application/json")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @Valid @RequestBody Coupon coupon) {
        log.info("Updating coupon with ID: {}", id);
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        log.info("Updated {} coupons.", coupon);
        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable Long id) {
        log.info("Deleting coupon with ID: {}", id);
        Coupon coupon=couponService.deleteCoupon(id);
        log.info("Deleting coupon with ID: {} successfully", id);
        return ResponseEntity.ok(coupon);
    }

    @PostMapping(value = "/coupons", consumes = "application/json")
    public ResponseEntity<Coupon> createCoupon(@Valid @RequestBody Coupon coupon) {
        log.info("Request to create a new coupon: {}", coupon);
        Coupon createdCoupon = couponService.createCoupon(coupon);
        log.info("Created new coupon: {}", createdCoupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @PostMapping(value = "/applicable-coupons",consumes = "application/json")
    public ResponseEntity<List<ApplicableCoupon>> getApplicableCoupons(@Valid @RequestBody CartWrapper cart) {
        log.info("Request to get applicable coupons for cart: {}", cart);
        List<ApplicableCoupon> applicableCoupons = couponService.getApplicableCoupons(cart.cart());
        log.info("Found {} applicable coupons.", applicableCoupons.size());
        return ResponseEntity.ok(applicableCoupons);
    }

    @PostMapping(value = "/apply-coupon/{id}",consumes = "application/json")
    public ResponseEntity<UpdatedCartDTO> applyCoupon(@PathVariable Long id, @RequestBody CartWrapper cart) {
        log.info("Request to apply coupon with ID {} to cart: {}", id, cart);
        UpdatedCartDTO updatedCart = couponService.applyCoupon(id, cart.cart());
        log.info("Coupon applied. Updated cart: {}", updatedCart);
        return ResponseEntity.ok(updatedCart);
    }
}
