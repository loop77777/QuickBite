package com.quickbite.service;


import com.quickbite.dao.OrderDAO;
import com.quickbite.exception.OrderNotFoundException;
import com.quickbite.model.Customer;
import com.quickbite.model.MenuItem;
import com.quickbite.model.Order;
import com.quickbite.model.OrderStatus;
import com.quickbite.model.Restaurant;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderService {
    private OrderDAO orderDAO;
    private Queue<Order> orderProcessingQueue = new LinkedList<>();

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public double calculateTotal(List<MenuItem> items) {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public double calculateTotal(List<MenuItem> items, double discountPercentage) {
        double subtotal = calculateTotal(items);
        double discount = subtotal * (discountPercentage / 100.0);
        return subtotal - discount;
    }

    public double calculateTotal(List<MenuItem> items, double discountPercentage, boolean expressDelivery) {
        double total = calculateTotal(items, discountPercentage);
        if (expressDelivery) {
            total += 50.0;
        }
        return total;
    }

    public Order createOrder(Customer customer, Restaurant restaurant, List<MenuItem> items, double finalAmount) {
        Order order = new Order(customer, restaurant, items, finalAmount);
        orderDAO.save(order);
        orderProcessingQueue.offer(order);
        return order;
    }

    public void processNextOrderInQueue() {
        if (!orderProcessingQueue.isEmpty()) {
            Order order = orderProcessingQueue.poll();
            order.setOrderStatus(OrderStatus.PREPARING);
            System.out.println(">> [QUEUE PROCESSOR] Order " + order.getOrderId() + " status updated to PREPARING.");
        }
    }

    public Order getOrder(String orderId) throws OrderNotFoundException {
        Order order = orderDAO.findById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order ID " + orderId + " not found!");
        }
        return order;
    }
}