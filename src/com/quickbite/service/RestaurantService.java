package com.quickbite.service;

import com.quickbite.dao.RestaurantDAO;
import com.quickbite.exception.RestaurantNotFoundException;
import com.quickbite.model.MenuItem;
import com.quickbite.model.Restaurant;

public class RestaurantService {
    private RestaurantDAO restaurantDAO;

    public RestaurantService(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurantDAO.save(restaurant.getRestaurantId(), restaurant);
    }

    public Restaurant getRestaurant(int id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantDAO.findById(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant with ID " + id + " not found!");
        }
        return restaurant;
    }

    public void importMenuFromCSV(int restaurantId, String[] itemNames, double[] prices, String[] categories) throws RestaurantNotFoundException {
        Restaurant restaurant = getRestaurant(restaurantId);
        for (int i = 0; i < itemNames.length; i++) {
            MenuItem item = new MenuItem(i + 1, itemNames[i], prices[i], categories[i]);
            restaurant.addMenuItem(item);
        }
    }
}