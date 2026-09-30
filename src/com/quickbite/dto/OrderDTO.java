package com.quickbite.dto;

import java.util.List;

public class OrderDTO {
    public int customerId;
    public int restaurantId;
    public List<Integer> itemIds;
    public String couponCode;
    public boolean isExpressDelivery;

    public OrderDTO(int customerId, int restaurantId, List<Integer> itemIds, String couponCode, boolean isExpressDelivery) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.itemIds = itemIds;
        this.couponCode = couponCode;
        this.isExpressDelivery = isExpressDelivery;
    }
}