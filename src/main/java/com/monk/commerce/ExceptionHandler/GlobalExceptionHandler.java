package com.monk.commerce.ExceptionHandler;

import com.monk.commerce.Coupon.Exception.CouponNotApplicableException;
import com.monk.commerce.Coupon.Exception.CouponNotFoundException;
import com.monk.commerce.Coupon.Exception.InvalidCouponDetailsException;
import com.monk.commerce.Coupon.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(int status, String error, String message, String path, Map<String, Object> additionalDetails) {
        ErrorResponse errorResponse = new ErrorResponse(status, error, message, path, additionalDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        response.put("error", "Unsupported Media Type");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Malformed JSON Request");
        response.put("message", "Could not read JSON: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("message", "Validation failed for one or more fields");

        Map<String, String> violations = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            violations.put(error.getField(), error.getDefaultMessage());
        }

        additionalDetails.put("violations", violations);
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed", ex.getMessage(), request.getDescription(false), additionalDetails);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource Not Found", ex.getMessage(), request.getDescription(false), null);
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotFoundException(CouponNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), "Coupon Not Found", ex.getMessage(), request.getDescription(false), null);
    }

    @ExceptionHandler(InvalidCouponDetailsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCouponDetailsException(InvalidCouponDetailsException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid Coupon Details", ex.getMessage(), request.getDescription(false), null);
    }

    @ExceptionHandler(CouponNotApplicableException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotApplicableException(CouponNotApplicableException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Coupon Not Applicable", ex.getMessage(), request.getDescription(false), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage(), request.getDescription(false), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage(), request.getDescription(false), null);
    }
}
