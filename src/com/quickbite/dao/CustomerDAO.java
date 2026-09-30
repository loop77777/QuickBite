package com.quickbite.dao;

import com.quickbite.model.Customer;

public interface CustomerDAO extends Repository<Customer> {
    Customer findByEmail(String email);
}