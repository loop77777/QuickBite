package com.quickbite.service;

import com.quickbite.exception.PaymentFailedException;
import com.quickbite.model.Wallet;

public class WalletPayment implements PaymentStrategy {
    private Wallet wallet;

    public WalletPayment(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean processPayment(double amount) throws PaymentFailedException {
        System.out.println("[WALLET] Debiting QuickBite Balance...");
        if (!wallet.debit(amount)) {
            throw new PaymentFailedException("Insufficient QuickBite Wallet balance! Current: ₹" + wallet.getBalance());
        }
        System.out.println("[WALLET] Debited ₹" + amount + " successfully. Remaining Balance: ₹" + wallet.getBalance());
        return true;
    }
}