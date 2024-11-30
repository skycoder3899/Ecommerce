package com.monk.commerce.Coupon.service;

import com.monk.commerce.Coupon.Exception.CouponNotApplicableException;
import com.monk.commerce.Coupon.Exception.CouponNotFoundException;
import com.monk.commerce.Coupon.dto.*;
import com.monk.commerce.Coupon.model.*;
import com.monk.commerce.Coupon.model.detail.BxGyCoupon;
import com.monk.commerce.Coupon.model.detail.ProductDetail;
import com.monk.commerce.Coupon.model.detail.ProductWiseCoupon;
import com.monk.commerce.Coupon.processor.CouponProcessor;
import com.monk.commerce.Coupon.processor.CouponProcessorFactory;
import com.monk.commerce.Coupon.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponProcessorFactory processorFactory;

    @Autowired
    public CouponService(CouponRepository couponRepository, CouponProcessorFactory processorFactory) {
        this.couponRepository = couponRepository;
        this.processorFactory = processorFactory;
    }

    public List<Coupon> getAllCoupon() {
        log.info("Fetching all coupons from the repository.");
        List<Coupon> coupons = couponRepository.findAll();
        log.info("Found {} coupons.", coupons.size());
        return coupons;
    }

    public Coupon createCoupon(Coupon coupon) {
        log.info("Creating a new coupon: {}", coupon);
        Coupon createdCoupon = couponRepository.save(coupon);
        log.info("Coupon created with ID: {}", createdCoupon.getId());
        return createdCoupon;
    }

    public List<ApplicableCoupon> getApplicableCoupons(Cart cart) {
        log.info("Checking applicable coupons for the cart: {}", cart);
        List<ApplicableCoupon> applicableCoupons = couponRepository.findAll().stream()
                .filter(coupon -> processorFactory.getProcessor(coupon.getType()).isApplicable(cart, coupon))
                .map(coupon -> ApplicableCoupon.builder()
                        .coupon_id(coupon.getId())
                        .type(coupon.getType())
                        .discount(calculateDiscount(cart, coupon))
                        .build())
                .collect(Collectors.toList());

        if (applicableCoupons.isEmpty()) {
            log.error("No applicable coupons found for cart: {}", cart);
            throw new CouponNotApplicableException("No applicable coupons found for the cart");
        }

        return applicableCoupons;
    }

    private double calculateDiscount(Cart cart, Coupon coupon) {
        log.debug("Calculating discount for coupon ID: {} and cart: {}", coupon.getId(), cart);
        double discount = processorFactory.getProcessor(coupon.getType()).calculateDiscount(cart, coupon);
        log.debug("Calculated discount: {}", discount);
        return discount;
    }

    public UpdatedCartDTO applyCoupon(Long couponId, Cart cart) {
        log.info("Applying coupon with ID: {} to cart: {}", couponId, cart);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon with ID " + couponId + " not found"));

        CouponProcessor processor = processorFactory.getProcessor(coupon.getType());

        if (!processor.isApplicable(cart, coupon)) {
            log.error("Coupon with ID: {} is not applicable to the cart: {}", couponId, cart);
            throw new CouponNotApplicableException("Coupon with ID " + couponId + " is not applicable to the cart");
        }

        List<ItemDTO> itemDTOList = cart.getItems().stream()
                .map(item -> {
                    double itemDiscount = calculateItemDiscount(item, cart, coupon);
                    return new ItemDTO(
                            (long) item.getProductId(),
                            item.getQuantity(),
                            item.getPrice(),
                            itemDiscount
                    );
                })
                .collect(Collectors.toList());

        double totalDiscount = processor.calculateDiscount(cart, coupon);
        double finalPrice = cart.getTotalPrice() - totalDiscount;

        log.info("Coupon applied. Total discount: {}. Final price: {}", totalDiscount, finalPrice);

        return new UpdatedCartDTO(
                itemDTOList,
                cart.getTotalPrice(),
                totalDiscount,
                finalPrice
        );
    }

    private double calculateItemDiscount(Item item, Cart cart, Coupon coupon) {
        double totalDiscount = 0.0;
        log.debug("Calculating item discount for item: {} and coupon: {}", item, coupon);

        if (coupon.getCouponDetail() instanceof BxGyCoupon) {
            BxGyCoupon bxGyCoupon = (BxGyCoupon) coupon.getCouponDetail();
            Map<Integer, Integer> cartProductQuantity = cart.getItems().stream()
                    .collect(Collectors.toMap(
                            Item::getProductId,
                            Item::getQuantity,
                            Integer::sum
                    ));

            List<ProductDetail> getProducts = bxGyCoupon.getGetProducts();
            for (ProductDetail getProduct : getProducts) {
                if (item.getProductId() == getProduct.getProductId()) {
                    int eligibleForFreeProducts = Math.min(
                            cartProductQuantity.getOrDefault(getProduct.getProductId(), 0) / getProduct.getQuantity(),
                            bxGyCoupon.getRepitionLimit()
                    );
                    totalDiscount = eligibleForFreeProducts * item.getPrice();
                    log.debug("BxGyCoupon: Product ID {} eligible for {} free items. Total discount: {}", item.getProductId(), eligibleForFreeProducts, totalDiscount);
                }
            }
        } else if (coupon.getCouponDetail() instanceof ProductWiseCoupon) {
            ProductWiseCoupon details = (ProductWiseCoupon) coupon.getCouponDetail();
            if (item.getProductId() == details.getProductId()) {
                totalDiscount = item.getPrice() * item.getQuantity() * (details.getDiscount() / 100);
                log.debug("ProductWiseCoupon: Product ID {} has a discount of {} on quantity {}. Total discount: {}", item.getProductId(), details.getDiscount(), item.getQuantity(), totalDiscount);
            }
        }

        return totalDiscount;
    }

    public Coupon getCouponById(Long id) {
        log.info("Fetching coupon with ID: {}", id);
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Coupon with ID " + id + " not found"));
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        log.info("Updating coupon with ID: {} {}", id,coupon);

        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Coupon with ID " + id + " not found"));

        existingCoupon.setType(coupon.getType());
        existingCoupon.setCouponDetail(coupon.getCouponDetail());
        return couponRepository.save(existingCoupon);
    }

    public Coupon deleteCoupon(Long id) {
        log.info("Deleting coupon with ID: {}", id);
        Optional<Coupon> coupon = couponRepository.findById(id);

        if (!coupon.isPresent()) {
            throw new CouponNotFoundException("Coupon with ID " + id + " not found");
        }
        couponRepository.deleteById(id);
        log.info("Coupon with ID {} has been deleted", id);

        return coupon.get();
    }

}
