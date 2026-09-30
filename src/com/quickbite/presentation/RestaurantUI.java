package com.quickbite.presentation;

import com.quickbite.model.MenuItem;
import com.quickbite.model.Restaurant;
import com.quickbite.service.RestaurantService;
import java.util.List;

public class RestaurantUI {
    private RestaurantService restaurantService;

    public RestaurantUI(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public void displayMenu(int restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        System.out.println("\n--- MENU FOR " + restaurant.getRestaurantName().toUpperCase() + " ---");
        List<MenuItem> menu = restaurant.getMenuItems();
        for (MenuItem item : menu) {
            if (item.getPrice() > 500) continue;
            System.out.println(item);
        }
    }
}