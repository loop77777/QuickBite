package com.quickbite.dao;


import com.quickbite.model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {
    private Map<String, Order> orderDb = new HashMap<>();

    @Override
    public void save(Order order) {
        orderDb.put(order.getOrderId(), order);
    }

    @Override
    public Order findById(String orderId) {
        return orderDb.get(orderId);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orderDb.values());
    }
}