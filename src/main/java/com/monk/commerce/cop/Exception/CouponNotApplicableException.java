package com.monk.commerce.cop.Exception;

public class CouponNotApplicableException extends RuntimeException {
    public CouponNotApplicableException(String message) {
        super(message);
    }
}
