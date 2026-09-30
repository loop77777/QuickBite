package com.quickbite.service;

import com.quickbite.exception.PaymentFailedException;

public interface PaymentStrategy {
    boolean processPayment(double amount) throws PaymentFailedException;
}