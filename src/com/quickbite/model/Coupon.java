package com.quickbite.model;

public class Coupon {
    private String couponCode;
    private double discountPercentage;

    public Coupon(String couponCode, double discountPercentage) {
        this.couponCode = couponCode;
        this.discountPercentage = discountPercentage;
    }

    public String getCouponCode() { return couponCode; }
    public double getDiscountPercentage() { return discountPercentage; }
}