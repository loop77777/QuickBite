package com.quickbite.delegate;

import com.quickbite.dto.CustomerDTO;
import com.quickbite.dto.OrderDTO;
import com.quickbite.model.Customer;
import com.quickbite.model.MenuItem;
import com.quickbite.model.Order;
import com.quickbite.model.OrderStatus;
import com.quickbite.model.Restaurant;
import com.quickbite.service.CouponService;
import com.quickbite.service.CustomerService;
import com.quickbite.service.OrderService;
import com.quickbite.service.PaymentStrategy;
import com.quickbite.service.RestaurantService;
import java.util.ArrayList;
import java.util.List;

public class QuickBiteBusinessDelegate {
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private OrderService orderService;
    private CouponService couponService;

    public QuickBiteBusinessDelegate(CustomerService cs, RestaurantService rs, OrderService os, CouponService cps) {
        this.customerService = cs;
        this.restaurantService = rs;
        this.orderService = os;
        this.couponService = cps;
    }

    public Customer registerCustomer(CustomerDTO dto) {
        return customerService.registerCustomer(dto);
    }

    public Order placeOrder(OrderDTO dto) throws Exception {
        Customer customer = customerService.getCustomer(dto.customerId);
        Restaurant restaurant = restaurantService.getRestaurant(dto.restaurantId);

        List<MenuItem> selectedItems = new ArrayList<>();
        for (MenuItem item : restaurant.getMenuItems()) {
            if (dto.itemIds.contains(item.getItemId())) {
                selectedItems.add(item);
            }
        }

        if (selectedItems.isEmpty()) {
            throw new IllegalArgumentException("No valid items selected for order!");
        }

        double discountPct = couponService.validateAndGetDiscount(dto.couponCode);
        double finalAmount = orderService.calculateTotal(selectedItems, discountPct, dto.isExpressDelivery);

        return orderService.createOrder(customer, restaurant, selectedItems, finalAmount);
    }

    public boolean executePayment(String orderId, PaymentStrategy strategy) throws Exception {
        Order order = orderService.getOrder(orderId);
        boolean success = strategy.processPayment(order.getTotalAmount());
        if (success) {
            order.setOrderStatus(OrderStatus.ACCEPTED);
            orderService.processNextOrderInQueue();
        }
        return success;
    }
}