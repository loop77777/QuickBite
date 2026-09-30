package com.quickbite.service;

import com.quickbite.exception.InvalidCouponException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CouponService {
    private Set<String> validCoupons = new HashSet<>();
    private Map<String, Double> discountMap = new HashMap<>();

    public CouponService() {
        addCoupon("WELCOME50", 50.0);
        addCoupon("QUICK10", 10.0);
    }

    public void addCoupon(String code, double percentage) {
        validCoupons.add(code.toUpperCase());
        discountMap.put(code.toUpperCase(), percentage);
    }

    public double validateAndGetDiscount(String code) throws InvalidCouponException {
        if (code == null || code.trim().isEmpty()) return 0.0;

        String cleanCode = code.toUpperCase().trim();
        if (!validCoupons.contains(cleanCode)) {
            throw new InvalidCouponException("Coupon Code '" + code + "' is invalid or expired!");
        }
        return discountMap.get(cleanCode);
    }
}