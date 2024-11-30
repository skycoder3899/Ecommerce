# Scalable E-Commerce Coupon System

This project is a modular, scalable implementation of a coupon system for an e-commerce platform. It uses Java and Spring Boot for the backend and follows best practices for maintainability and extensibility.

## Features

### Implemented Cases
1. **Cart-Wise Discount Coupons**: Coupons that apply discounts based on the total value of the cart.
    - Example: "10% off for carts above $100."
    - Uses the `CartWiseProcessor` to compute discounts dynamically.
2. **Strategy Pattern for Coupon Processing**: Implemented using the `CouponProcessor` interface and specific processors like `CartWiseProcessor`.
3. **Global Exception Handling**: A centralized `GlobalExceptionHandler` manages errors and ensures consistent API responses.
4. **RESTful API Endpoints**: Endpoints to apply coupons, exposing functionality via the `CouponController`.
5. **DTOs for Input/Output**: Separates domain models from API payloads for clarity and flexibility.
6. **Database Integration**: Uses JPA for database operations, with the `CouponRepository` to manage coupon entities.

### Unimplemented Cases
1. **Coupon Expiry and Validation**: Logic to handle expiry dates for coupons.
    - Reason: Requires date-based validations and integration with time-sensitive operations.

### Limitations
1. **No Authentication**: Assumes the system is integrated with a user management system but does not enforce authentication.

### Assumptions
1. Coupons are unique and identified by their code.
2. Cart values are always positive and valid.
3. The system has access to a properly configured database for storing and retrieving coupons.
4. The coupon type is provided as a string in API requests and matches the processor keys.

