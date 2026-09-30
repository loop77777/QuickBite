package com.quickbite.dao;


import com.quickbite.dto.GenericRepositoryImpl;
import com.quickbite.model.Customer;

public class CustomerDAOImpl extends GenericRepositoryImpl<Customer> implements CustomerDAO {
    @Override
    public Customer findByEmail(String email) {
        for (Customer c : findAll()) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }
}