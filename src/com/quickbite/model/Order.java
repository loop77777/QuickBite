package com.quickbite.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Order {
    private static int orderCounter = 1000;
    private String orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<MenuItem> foodItems;
    private double totalAmount;
    private OrderStatus orderStatus;

    public Order(Customer customer, Restaurant restaurant, List<MenuItem> foodItems, double totalAmount) {
        this.orderId = generateOrderId();
        this.customer = customer;
        this.restaurant = restaurant;
        this.foodItems = foodItems;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.PLACED;
    }

    private synchronized String generateOrderId() {
        StringBuilder sb = new StringBuilder();
        sb.append("ORD").append(new SimpleDateFormat("yyyyMMdd").format(new Date())).append(++orderCounter);
        return sb.toString();
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<MenuItem> getFoodItems() { return foodItems; }
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    public String generateInvoice() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n======================================================\n");
        sb.append("                 QUICKBITE OFFICIAL INVOICE          \n");
        sb.append("======================================================\n");
        sb.append("Order Ref    : ").append(orderId).append("\n");
        sb.append("Date         : ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())).append("\n");
        sb.append("Customer     : ").append(customer.getName()).append(" (").append(customer.getMobile()).append(")\n");
        sb.append("Restaurant   : ").append(restaurant.getRestaurantName()).append("\n");
        sb.append("------------------------------------------------------\n");
        sb.append("Items Ordered:\n");

        Iterator<MenuItem> iterator = foodItems.iterator();
        while (iterator.hasNext()) {
            MenuItem item = iterator.next();
            sb.append(String.format(" - %-25s : ₹%.2f\n", item.getItemName(), item.getPrice()));
        }
        sb.append("------------------------------------------------------\n");
        sb.append(String.format("TOTAL AMOUNT PAID : ₹%.2f\n", totalAmount));
        sb.append("Status            : ").append(orderStatus).append("\n");
        sb.append("======================================================\n");
        return sb.toString();
    }
}
