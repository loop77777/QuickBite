package com.quickbite.model;

public class DeliveryPartner extends User {
    private String vehicleNumber;
    private boolean isAvailable;

    public DeliveryPartner(int id, String name, String email, long mobile, String vehicleNumber) {
        super(id, name, email, mobile);
        this.vehicleNumber = vehicleNumber;
        this.isAvailable = true;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public void login() {
        System.out.println(">> Delivery Partner [" + getName() + "] is online.");
    }

    @Override
    public void logout() {
        System.out.println("<< Delivery Partner [" + getName() + "] is offline.");
    }
}
