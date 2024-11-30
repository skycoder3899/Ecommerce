package com.monk.commerce.cop.processor;

import com.monk.commerce.cop.model.CouponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CouponProcessorFactory {
    private final Map<CouponType, CouponProcessor> processors;

    @Autowired
    public CouponProcessorFactory(BxGyProcessor bxGyProcessor, CartWiseProcessor cartWiseProcessor, ProductWiseProcessor productWiseProcessor) {
        processors = Map.of(
                CouponType.BXGY, bxGyProcessor,
                CouponType.CART_WISE, cartWiseProcessor,
                CouponType.PRODUCT_WISE, productWiseProcessor
        );
    }

    public CouponProcessor getProcessor(CouponType type) {
        return processors.get(type);
    }
}
