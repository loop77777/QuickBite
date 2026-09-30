# QuickBite

QuickBite is a Java console demonstration of an online food-delivery workflow. It models customers, restaurants, menus, orders, discounts, and payment processing with a layered design. The current application runs a predefined end-to-end scenario from `main`; it is not an interactive CLI and does not use a database or external payment gateway.

## Features

- Registers a customer after validating the email format, then demonstrates login.
- Registers a restaurant and adds menu items from parallel name, price, and category arrays.
- Displays a restaurant menu, filtering out items priced above ₹500.
- Creates an order from selected menu item IDs and calculates the total.
- Applies coupons `WELCOME50` (50% off) or `QUICK10` (10% off). An empty coupon means no discount; an unknown coupon raises `InvalidCouponException`.
- Adds a ₹50 express-delivery fee when requested.
- Processes payments through a strategy interface with wallet, UPI, and card implementations. The sample scenario uses wallet payment; UPI and card processing are demonstrations, not real financial integrations.
- Updates order status after successful payment and moves the next order in the processing queue to `PREPARING`.
- Demonstrates typed domain exceptions and `finally` cleanup behavior for a missing-customer lookup.
- Prints an admin monitoring report with the order and successful transaction counts, then requests JVM garbage collection.

## Application Flow

Running the application performs the following sample scenario:

1. Initializes in-memory DAO and service instances.
2. Registers the `Spice Garden` restaurant and adds four sample menu items.
3. Registers and logs in a sample customer with a ₹1,000 wallet balance.
4. Displays the restaurant menu.
5. Places an express order for three menu items using `WELCOME50` and prints the payable amount.
6. Pays from the customer's wallet, updates the order status, and prints an invoice when payment succeeds.
7. Tries to look up a nonexistent customer to demonstrate exception handling.
8. Prints the admin monitoring report and triggers a garbage-collection request.

The sample values and workflow are defined in `src/QuickBiteApp.java`.

## Project Structure

```text
QuickBite/
├── instruct.md                    # Project problem statement and design requirements
├── README.md
└── src/
    ├── QuickBiteApp.java          # Application entry point and sample workflow
    └── com/quickbite/
        ├── dao/                   # DAO contracts and in-memory implementations
        ├── delegate/              # Business delegate between UI and services
        ├── dto/                   # Customer/order DTOs and generic repository implementation
        ├── exception/             # Domain-specific exceptions
        ├── model/                 # Users, customers, restaurants, orders, wallets, and enums
        ├── presentation/          # Customer, restaurant, and admin console presentation helpers
        └── service/               # Business rules, coupons, order processing, and payment strategies
```

### Package Responsibilities

- `com.quickbite.model`: Domain objects such as `Customer`, `Restaurant`, `MenuItem`, `Order`, `Wallet`, and order/payment status enums.
- `com.quickbite.dto`: Data transfer objects used to supply registration and order information. `GenericRepositoryImpl` provides the generic in-memory repository implementation.
- `com.quickbite.dao`: Repository contracts and DAO implementations for customers, restaurants, and orders.
- `com.quickbite.service`: Customer and restaurant operations, order totals and queue processing, coupon validation, and payment strategies.
- `com.quickbite.delegate`: `QuickBiteBusinessDelegate` coordinates services for presentation-layer requests.
- `com.quickbite.presentation`: Helpers that invoke customer and restaurant workflows and display the admin report.
- `com.quickbite.exception`: Custom exceptions for missing customers, restaurants, or orders, failed payments, and invalid coupons.

## Architecture and Design

The code is organized into presentation, business/service, DAO, and model/data responsibilities. DAOs currently store objects in Java collections, so all data is lost when the process exits.

Patterns and Java concepts demonstrated include:

- **DAO and generic repository:** Separates storage operations from service logic.
- **Business Delegate:** Routes presentation requests through `QuickBiteBusinessDelegate` to the appropriate services.
- **DTO:** Carries customer and order input data into the business layer.
- **Strategy:** `PaymentStrategy` allows payment implementations to be selected interchangeably.
- **Queue processing:** Orders are placed in an in-memory processing queue and processed sequentially.
- **Other language concepts:** Inheritance and polymorphism, overloaded total-calculation methods, custom exceptions, regular-expression email validation, collections, and explicit `System.gc()` demonstration.

## Requirements

- Java Development Kit (JDK) with `javac` and `java` available on `PATH`.
- No third-party libraries or build tool are required.

## Compile and Run

Run these commands from the project root.

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$javaFiles = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $javaFiles
java -cp out QuickBiteApp
```

### macOS / Linux

```sh
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out QuickBiteApp
```

The program writes its progress and results to the console. It uses Indian rupee amounts and deterministic sample inputs; there are no prompts to enter data.

## Current Scope

This is an educational in-memory application. It does not currently provide persistent storage, user-entered console menus, real authentication, a real CSV-file parser, delivery-partner assignment, or live UPI/card gateway integration. The menu-import method accepts arrays, and the payment strategies simulate processing. The current sample run exercises wallet payment.
