package com.quickbite.service;


import com.quickbite.dao.CustomerDAO;
import com.quickbite.dto.CustomerDTO;
import com.quickbite.exception.CustomerNotFoundException;
import com.quickbite.model.Customer;
import java.util.regex.Pattern;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer registerCustomer(CustomerDTO dto) throws IllegalArgumentException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, dto.email)) {
            throw new IllegalArgumentException("Invalid Email Format: " + dto.email);
        }

        Customer customer = new Customer(dto.id, dto.name, dto.email, dto.mobile, dto.address, dto.initialWallet);
        customerDAO.save(customer.getId(), customer);
        return customer;
    }

    public Customer getCustomer(int id) throws CustomerNotFoundException {
        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist!");
        }
        return customer;
    }
}