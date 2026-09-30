package com.quickbite.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int restaurantId;
    private String restaurantName;
    private String location;
    private double rating;
    private List<MenuItem> menuItems;

    public Restaurant(int restaurantId, String restaurantName, String location, double rating) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.location = location;
        this.rating = rating;
        this.menuItems = new ArrayList<>();
    }

    public int getRestaurantId() { return restaurantId; }
    public String getRestaurantName() { return restaurantName; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public List<MenuItem> getMenuItems() { return menuItems; }

    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
    }
}