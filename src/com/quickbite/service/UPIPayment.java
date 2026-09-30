package com.quickbite.service;


import com.quickbite.exception.PaymentFailedException;

public class UPIPayment implements PaymentStrategy {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) throws PaymentFailedException {
        System.out.println("[UPI] Contacting PSP for handle " + upiId + "...");
        if (amount > 10000) {
            throw new PaymentFailedException("UPI Transaction limit exceeded!");
        }
        System.out.println("[UPI] Successfully debited ₹" + amount + " via UPI.");
        return true;
    }
}