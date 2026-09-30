package com.quickbite.dto;


public class CustomerDTO {
    public int id;
    public String name;
    public String email;
    public long mobile;
    public String address;
    public double initialWallet;

    public CustomerDTO(int id, String name, String email, long mobile, String address, double initialWallet) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.initialWallet = initialWallet;
    }
}