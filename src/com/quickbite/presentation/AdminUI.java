package com.quickbite.presentation;


import com.quickbite.dao.OrderDAO;

public class AdminUI {
    public static void displayMonitoringReport(OrderDAO orderDAO, int totalTransactions) {
        System.out.println("\n--- ADMIN SYSTEM MONITORING REPORT ---");
        System.out.println("Total Orders Logged in System: " + orderDAO.findAll().size());
        System.out.println("Total Financial Transactions Executed: " + totalTransactions);
    }
}