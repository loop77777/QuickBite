1. Complete Problem Statement
   Title: QuickBite — Online Food Delivery & Restaurant Management System
   Executive Summary
   QuickBite is an enterprise-grade food delivery and restaurant management ecosystem (similar to Swiggy and Zomato). The platform manages multi-actor workflows comprising Customers, Restaurant Owners, Admins, and Delivery Partners. The system provides seamless menu management, dynamic order routing through automated processing queues, multi-channel payment integrations, real-time status tracking, coupon discount applications, and system-wide monitoring.

Architectural Requirements
The system must adhere to a 4-Layer Enterprise Architecture:

Presentation Layer: Manages interactive CLI/Console menus, captures user inputs, and presents sanitized view outputs (CustomerUI, RestaurantUI, OrderUI, AdminUI).

Business Layer: Encapsulates business validation rules, discount algorithms, wallet ledgers, and transaction management (CustomerService, RestaurantService, OrderService, PaymentService, CouponService).

DAO (Data Access Object) Layer: Provides data abstraction and persistent operation contracts (CustomerDAO, RestaurantDAO, OrderDAO, PaymentDAO and their Impl classes).

Data Layer (In-Memory Enterprise Repository): Uses Java Collection Framework instances (HashMap, ArrayList, HashSet, LinkedList) as an in-memory database.

Core OO & Structural Requirements
SOLID Principles: Strict adherence to Single Responsibility, Open/Closed (Payment Strategy abstraction), Liskov Substitution, Interface Segregation, and Dependency Inversion (DAO interfaces & generic repositories).

Design Patterns:

DAO Pattern: Decouples business logic from data access.

Business Delegate Pattern: Decouples Presentation Layer components from service implementations via QuickBiteBusinessDelegate.

Transfer Object (DTO) Pattern: Uses immutable DTOs (CustomerDTO, OrderDTO, RestaurantDTO) to transfer data across layer boundaries.

Iterator Pattern: Sequentially traverses order collections and active menus.

Strategy Pattern: Supports interchangeable payment processors (UPI, Credit/Debit Card, QuickBite Wallet).

Core Language Concepts: Polymorphic method overloading & overriding, equals() & hashCode() contracts, custom checked exceptions, StringBuilder & thread-safe StringBuffer string manipulations, array parsing (CSV menu imports), autoboxing/unboxing, generic repositories (Repository<T>), regex validations, and System.gc() demonstration.

+---------------------------------------------------------------------------------+
|                                    MODEL LAYER                                  |
+---------------------------------------------------------------------------------+

                      +----------------------------------+
                      |         <<abstract>>             |
                      |              User                |
                      +----------------------------------+
                      | - id: int                        |
                      | - name: String                   |
                      | - email: String                  |
                      | - mobile: long                   |
                      +----------------------------------+
                      | + login(): void                  |
                      | + logout(): void                 |
                      +----------------------------------+
                                       ▲
                                       │ (IS-A Inheritance)
         ┌─────────────────────────────┼─────────────────────────────┐
         │                             │                             │
+------------------+         +-------------------+         +-------------------+
|     Customer     |         |  RestaurantOwner  |         |       Admin       |
+------------------+         +-------------------+         +-------------------+
| - wallet: Wallet |         | - restaurantId:int|         | - adminLevel: int |
+------------------+         +-------------------+         +-------------------+
│ (HAS-A 1:1)
▼
+------------------+
|      Wallet      |
+------------------+
| - balance: double|
+------------------+

+--------------------+          +--------------------+          +--------------------+
|     Restaurant     | 1      * |      MenuItem      | *      * |       Order        |
+--------------------+--------->+--------------------+<--------->+--------------------+
| - id: int          | (HAS-A)  | - id: int          | (HAS-A)  | - id: int          |
| - name: String     |          | - name: String     |          | - customer:Customer|
| - location: String |          | - price: double    |          | - items: List      |
| - rating: double   |          | - category: String |          | - amount: double   |
| - menu: List       |          +--------------------+          | - status:Enum      |
+--------------------+                                          +--------------------+

+---------------------------------------------------------------------------------+
|                                  DATA ACCESS LAYER                              |
+---------------------------------------------------------------------------------+

+---------------------------+                 +----------------------------------+
|   <<interface>>           |                 |          <<interface>>           |
|   Repository<T>           |                 |           CustomerDAO            |
+---------------------------+                 +----------------------------------+
| + save(entity: T): void   |                 | + save(c: Customer): void        |
| + findById(id: int): T    |                 | + findById(id: int): Customer    |
| + findAll(): List<T>      |                 | + findByEmail(e: String):Customer|
+---------------------------+                 +----------------------------------+
▲                                                ▲
│ (Implements)                                   │ (Implements)
+---------------------------+                 +----------------------------------+
| GenericRepositoryImpl<T>  |                 |         CustomerDAOImpl          |
+---------------------------+                 +----------------------------------+

+---------------------------------------------------------------------------------+
|                               BUSINESS & DELEGATE LAYER                         |
+---------------------------------------------------------------------------------+

                      +------------------------------------+
                      |     QuickBiteBusinessDelegate      |
                      +------------------------------------+
                      | - customerService: CustomerService |
                      | - restaurantService:RestService    |
                      | - orderService: OrderService       |
                      +------------------------------------+
                      | + registerCustomer(dto): Customer  |
                      | + placeOrder(dto): Order           |
                      | + processPayment(orderId, Strategy)|
                      +------------------------------------+
                                         │
        ┌────────────────────────────────┼────────────────────────────────┐
        ▼                                ▼                                ▼
+--------------------+        +--------------------+           +--------------------+
|  CustomerService   |        | RestaurantService  |           |    OrderService    |
+--------------------+        +--------------------+           +--------------------+
| - dao: CustomerDAO |        | - dao:RestDAO      |           | - dao: OrderDAO    |
+--------------------+        +--------------------+           | - queue: Queue     |
+--------------------+

+---------------------------------------------------------------------------------+
|                              STRATEGY DESIGN PATTERN                            |
+---------------------------------------------------------------------------------+

                     +--------------------------------------+
                     |            <<interface>>             |
                     |           PaymentStrategy            |
                     +--------------------------------------+
                     | + pay(amount: double): PaymentResult |
                     +--------------------------------------+
                                         ▲
         ┌───────────────────────────────┼───────────────────────────────┐
         │                               │                               │
+------------------+           +-------------------+           +-------------------+
|   UPIPayment     |           |    CardPayment    |           |   WalletPayment   |
+------------------+           +-------------------+           +-------------------+
| - upiId: String  |           | - cardNumber:Str  |           | - wallet: Wallet  |
+------------------+           +-------------------+           +-------------------+

src/
└── com/quickbite/
├── model/               (Domain Entities & Enums)
│   ├── OrderStatus.java
│   ├── PaymentStatus.java
│   ├── User.java
│   ├── Wallet.java
│   ├── Customer.java
│   ├── Admin.java
│   ├── DeliveryPartner.java
│   ├── MenuItem.java
│   ├── Restaurant.java
│   ├── Order.java
│   └── Coupon.java
├── dto/                 (Data Transfer Objects)
│   ├── CustomerDTO.java
│   └── OrderDTO.java
├── exception/           (Custom Domain Exceptions)
│   ├── CustomerNotFoundException.java
│   ├── RestaurantNotFoundException.java
│   ├── OrderNotFoundException.java
│   ├── PaymentFailedException.java
│   └── InvalidCouponException.java
├── dao/                 (Data Access Layer & Repositories)
│   ├── Repository.java
│   ├── GenericRepositoryImpl.java
│   ├── CustomerDAO.java
│   ├── CustomerDAOImpl.java
│   ├── RestaurantDAO.java
│   ├── RestaurantDAOImpl.java
│   ├── OrderDAO.java
│   └── OrderDAOImpl.java
├── service/             (Business Layer & Strategy Pattern)
│   ├── PaymentStrategy.java
│   ├── UPIPayment.java
│   ├── CardPayment.java
│   ├── WalletPayment.java
│   ├── CustomerService.java
│   ├── RestaurantService.java
│   ├── CouponService.java
│   └── OrderService.java
├── delegate/            (Business Delegate Pattern)
│   └── QuickBiteBusinessDelegate.java
└── presentation/        (Presentation Layer & Drivers)
├── CustomerUI.java
├── RestaurantUI.java
├── AdminUI.java
└── QuickBiteApp.java

