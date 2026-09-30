package com.quickbite.presentation;

import com.quickbite.delegate.QuickBiteBusinessDelegate;
import com.quickbite.dto.CustomerDTO;
import com.quickbite.dto.OrderDTO;
import com.quickbite.model.Customer;
import com.quickbite.model.Order;
import com.quickbite.model.User;
import com.quickbite.service.PaymentStrategy;

public class CustomerUI {
    private QuickBiteBusinessDelegate delegate;

    public CustomerUI(QuickBiteBusinessDelegate delegate) {
        this.delegate = delegate;
    }

    public Customer registerAndLogin(CustomerDTO dto) {
        Customer customer = delegate.registerCustomer(dto);
        User userRef = customer;
        userRef.login();
        return customer;
    }

    public Order submitOrder(OrderDTO dto) throws Exception {
        return delegate.placeOrder(dto);
    }

    public boolean payForOrder(String orderId, PaymentStrategy strategy) throws Exception {
        return delegate.executePayment(orderId, strategy);
    }
}