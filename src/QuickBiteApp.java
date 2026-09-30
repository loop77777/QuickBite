import com.quickbite.dao.*;
import com.quickbite.delegate.QuickBiteBusinessDelegate;
import com.quickbite.dto.CustomerDTO;
import com.quickbite.dto.OrderDTO;
import com.quickbite.exception.*;
import com.quickbite.model.*;
import com.quickbite.presentation.*;
import com.quickbite.service.*;

import java.util.Arrays;
import java.util.List;

public class QuickBiteApp {

    public static int totalTransactionsProcessed = 0;

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("    WELCOME TO QUICKBITE ONLINE FOOD DELIVERY PLATFORM (2026)    ");
        System.out.println("=================================================================\n");

        // 1. DAO Layer Initialization
        CustomerDAO customerDAO = new CustomerDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        // 2. Business Layer Initialization
        CustomerService customerService = new CustomerService(customerDAO);
        RestaurantService restaurantService = new RestaurantService(restaurantDAO);
        OrderService orderService = new OrderService(orderDAO);
        CouponService couponService = new CouponService();

        // 3. Delegate & UI Layer Initialization
        QuickBiteBusinessDelegate delegate = new QuickBiteBusinessDelegate(customerService, restaurantService, orderService, couponService);
        CustomerUI customerUI = new CustomerUI(delegate);
        RestaurantUI restaurantUI = new RestaurantUI(restaurantService);

        // --- STEP 1: RESTAURANT & MENU ONBOARDING ---
        System.out.println(">>> STEP 1: Onboarding Restaurants & Parsing CSV Menus...");
        Restaurant r1 = new Restaurant(101, "Spice Garden", "Indiranagar, Bengaluru", 4.8);
        restaurantService.registerRestaurant(r1);

        String[] items = {"Paneer Butter Masala", "Garlic Naan", "Veg Biryani", "Gulab Jamun"};
        double[] prices = {280.00, 50.00, 220.00, 80.00};
        String[] categories = {"Main Course", "Bread", "Main Course", "Dessert"};

        try {
            restaurantService.importMenuFromCSV(101, items, prices, categories);
            System.out.println("Menu imported successfully for " + r1.getRestaurantName());
        } catch (RestaurantNotFoundException e) {
            System.err.println(e.getMessage());
        }

        // --- STEP 2: CUSTOMER REGISTRATION & LOGIN ---
        System.out.println("\n>>> STEP 2: Customer Registration & Auth Validation...");
        Customer customer = null;
        try {
            CustomerDTO customerDTO = new CustomerDTO(1, "Aarav Sharma", "aarav.sharma@example.com", 9876543210L, "Koramangala, Bengaluru", 1000.00);
            customer = customerUI.registerAndLogin(customerDTO);
        } catch (Exception e) {
            System.err.println("Registration Error: " + e.getMessage());
        }

        // Display Menu using Presentation Component
        try {
            restaurantUI.displayMenu(101);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // --- STEP 3: ORDER PLACEMENT & DISCOUNT APPLICATION ---
        System.out.println("\n>>> STEP 3: Placing Food Order with Coupon Validation...");
        Order currentOrder = null;
        try {
            List<Integer> selectedItems = Arrays.asList(1, 2, 3);
            OrderDTO orderDTO = new OrderDTO(1, 101, selectedItems, "WELCOME50", true);

            currentOrder = customerUI.submitOrder(orderDTO);
            System.out.println("Order Created Successfully! Order Ref ID: " + currentOrder.getOrderId());

            Double amountObj = currentOrder.getTotalAmount();
            double amountPrimitive = amountObj;
            System.out.println("Calculated Final Payable Amount: ₹" + amountPrimitive);

        } catch (InvalidCouponException e) {
            System.err.println("Coupon Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Order Error: " + e.getMessage());
        }

        // --- STEP 4: PAYMENT PROCESSING (Strategy Pattern) ---
        System.out.println("\n>>> STEP 4: Processing Payment using Wallet Strategy...");
        if (currentOrder != null) {
            try {
                PaymentStrategy paymentStrategy = new WalletPayment(customer.getWallet());
                boolean paymentSuccess = customerUI.payForOrder(currentOrder.getOrderId(), paymentStrategy);

                if (paymentSuccess) {
                    totalTransactionsProcessed++;
                    System.out.println(currentOrder.generateInvoice());
                }
            } catch (PaymentFailedException e) {
                System.err.println("Payment Refused: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("System Error: " + e.getMessage());
            }
        }

        // --- STEP 5: EXCEPTION HANDLING DEMO ---
        System.out.println("\n>>> STEP 5: Executing Resilience Tests (Exception Handling)...");
        try {
            System.out.println("Testing Invalid Customer Lookup:");
            customerService.getCustomer(999);
        } catch (CustomerNotFoundException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        } finally {
            System.out.println("FINALLY BLOCK: Cleanup routines executed successfully.");
        }

        // --- STEP 6: GARBAGE COLLECTION & ADMIN REPORTING ---
        System.out.println("\n>>> STEP 6: System Garbage Collection & Monitoring...");
        AdminUI.displayMonitoringReport(orderDAO, totalTransactionsProcessed);

        System.out.println("Requesting explicit JVM Garbage Collection...");
        System.gc();
        System.out.println("Garbage Collection sweep triggered.");

        System.out.println("\n=================================================================");
        System.out.println("          QUICKBITE SYSTEM EXECUTION FINISHED SAFELY             ");
        System.out.println("=================================================================");
    }
}