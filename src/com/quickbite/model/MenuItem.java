package com.quickbite.model;

public class MenuItem {
    private int itemId;
    private String itemName;
    private double price;
    private String category;

    public MenuItem(int itemId, String itemName, double price, String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }

    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("Item #%-3d | %-20s | Category: %-10s | Price: ₹%.2f", itemId, itemName, category, price);
    }
}
