package com.quickbite.dao;


import com.quickbite.model.Order;
import java.util.List;

public interface OrderDAO {
    void save(Order order);
    Order findById(String orderId);
    List<Order> findAll();
}