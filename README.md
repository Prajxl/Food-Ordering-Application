Food Order Management System

A Spring Boot REST API for managing customers, restaurants, menu items, orders, order items, and payments. The project follows a layered backend architecture and implements CRUD operations, entity relationships, validations, order processing, and payment management.

🚀 Features

Customer Management

Create customer

Get all customers

Get customer by ID

Update customer

Delete customer

Find customer by contact

Find customer by email

Find customer by name

Restaurant Management

Add restaurant

Get all restaurants

Get restaurant by ID

Update restaurant

Delete restaurant

Find restaurants by location

Find restaurants by name

Find restaurants above a specified rating

Get menu items of a restaurant

Menu Item Management

Add menu items to a restaurant

Get all menu items

Get menu item by ID

Update item availability

Sort menu items by price

Find menu items by name

Get all menu items belonging to a restaurant

Order Management

Place an order

Get all orders

Get all orders of a customer

Get order by ID

Update order status

Cancel an order

Get orders by status

Get orders by date

Retrieve orders based on restaurant/order relationships

Order Item Management

Add items to an existing order before preparation

Update item quantity

Remove an item from an order before delivery

Get all order items of an order

Payment Management

Get payment by ID

Get payment by order

Get payments by status

Get payments by payment method

Update payment status

🛠️ Technologies Used

Java

Spring Boot

Spring Data JPA

Hibernate

REST API

PostgreSQL

Maven

Postman

Git & GitHub

🏗️ Project Architecture

The application follows a layered architecture:

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

Layers

Controller – Handles HTTP requests and API endpoints.

Service – Contains business logic and validations.

Repository – Communicates with the database using Spring Data JPA.

Entity – Represents database tables and relationships.

DTO – Used for transferring request data where required.

Exception Handling – Handles invalid requests and business rule violations.

🗃️ Main Entities

The project contains the following major entities:

Customer
   │
   │ 1 : Many
   ▼
 Order ───────── 1 : 1 ───────── Payment
   │
   │ 1 : Many
   ▼
OrderItem
   │
   │ Many : 1
   ▼
MenuItem
   │
   │ Many : 1
   ▼
Restaurant

Relationships

One Customer can place multiple Orders.

One Order belongs to one Customer.

One Order can contain multiple OrderItems.

Each OrderItem refers to one MenuItem.

One Restaurant can have multiple MenuItems.

One Order has one Payment.

JPA relationships are implemented using annotations such as:

@OneToMany

@ManyToOne

@OneToOne

@JoinColumn

@Enumerated

📌 Example API Endpoints

The exact base path may vary depending on the controller mappings used in the project.

Customer

POST   /api/customer
GET    /api/customer
GET    /api/customer/{id}
PUT    /api/customer/{id}
DELETE /api/customer/{id}

Restaurant

POST   /api/restaurant
GET    /api/restaurant
GET    /api/restaurant/{id}
PUT    /api/restaurant/{id}
DELETE /api/restaurant/{id}

Menu Item

POST   /api/menuitem
GET    /api/menuitem
GET    /api/menuitem/{id}
PATCH  /api/menuitem/{id}

Order

POST   /api/order/placeorder
GET    /api/order
GET    /api/order/{id}
PATCH  /api/order/{id}

Payment

GET    /api/payment/{paymentId}
GET    /api/payment/order/{orderId}
PATCH  /api/payment/{paymentId}

🔄 Order Flow

The typical order flow is:

Customer
   ↓
Select Restaurant
   ↓
Select Menu Items
   ↓
Place Order
   ↓
Create Order Items
   ↓
Calculate Total Amount
   ↓
Process Payment
   ↓
Update Order Status
   ↓
Preparing Food
   ↓
Out for Delivery
   ↓
Delivered

The application also supports order cancellation based on the order's current status.

🔐 Validation & Business Logic

The application includes validations and business rules such as:

Customer contact number validation

Pincode validation

Order quantity must be at least 1

Menu item availability checking

Order status validation

Payment status management

Preventing invalid order modifications after the order progresses

Maintaining relationships between customers, orders, restaurants, menu items, and payments

🧪 Testing

The REST APIs were tested using Postman.

Testing includes:

CRUD operations

Request/response validation

Relationship testing

Order placement

Order status updates

Payment status updates

Invalid input handling

Exception handling

⚙️ Setup & Installation

1. Clone the repository

git clone https://github.com/<your-username>/<your-repository>.git
cd <your-repository>

2. Configure PostgreSQL

Create a PostgreSQL database:

CREATE DATABASE food_order_db;

Update your application.properties or application.yml:

spring.datasource.url=jdbc:postgresql://localhost:5432/food_order_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3. Build the project

mvn clean install

4. Run the application

mvn spring-boot:run

The application will normally start at:

http://localhost:8080

📂 Project Structure

src
└── main
    ├── java
    │   └── com.jsp.foodorder.food_order_app
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── entity
    │       ├── dto
    │       └── exception
    │
    └── resources
        └── application.properties

🎯 Learning Outcomes

Through this project, I worked with:

Building RESTful APIs using Spring Boot

Spring MVC architecture

Spring Data JPA and Hibernate

Entity relationships and mappings

PostgreSQL database integration

CRUD operations

DTO-based request handling

Business logic implementation

Exception handling

Input validation

Order and payment workflows

API testing with Postman

Git and GitHub project management

👨‍💻 Author

Prajwal

Computer Science & Engineering
Java Backend / Spring Boot Developer

GitHub: Prajxl

Portfolio: Prajwal Portfolio

📄 License

This project is developed for learning and portfolio purposes.
