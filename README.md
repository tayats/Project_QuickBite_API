# 🍕 QuickBite REST API

[![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.18-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![H2 Database](https://img.shields.io/badge/Database-H2_In--Memory-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.h2database.com/)

An enterprise-grade online food ordering and delivery RESTful microservice built with **Java 11**, **Spring Boot**, **Spring Data JPA**, and an embedded **H2 Relational Database**.

The platform is designed following the **3-Tier Layered Architecture** with strict domain validation, relational mapping (`@ManyToOne`), dynamic delivery fee engines, and standardized HTTP response handling.

---

## 🏛️ System Architecture

[ Client / Web Browser / Mobile App ] │ ▼ (HTTP / JSON Payloads) ┌─────────────────────────────────────────────────────────┐ │ 1. Presentation Layer (@RestController) │ │ • RestaurantController, MenuItemController, │ │ OrderController │ │ • Route mapping, PathVariables, QueryParams │ │ • ResponseEntity status codes & @ExceptionHandler │ └────────────────────────┬────────────────────────────────┘ │ ▼ (DTOs / Domain Models) ┌─────────────────────────────────────────────────────────┐ │ 2. Business Logic Layer (@Service) │ │ • RestaurantService, MenuItemService, OrderService │ │ • Input validation & minimum spend enforcement │ │ • Dynamic delivery fee engine ($0 over $30) │ └────────────────────────┬────────────────────────────────┘ │ ▼ (Entity Persistence) ┌─────────────────────────────────────────────────────────┐ │ 3. Data Access Layer (Spring Data JPA / @Repository) │ │ • RestaurantRepository, MenuItemRepository, │ │ OrderRepository │ │ • Derived queries without raw SQL boilerplate │ └────────────────────────┬────────────────────────────────┘ │ ▼ (Relational SQL Execution) ┌─────────────────────────────────────────────────────────┐ │ 4. Persistence Layer (H2 Relational Database) │ │ • Tables: restaurants, menu_items, customer_orders │ └─────────────────────────────────────────────────────────┘


---

## 🗄️ Relational Data Schema

┌─────────────────────────────────┐ │ RESTAURANTS │ ├─────────────────────────────────┤ │ PK id (IDENTITY) │ │ name │ │ cuisine_type │ │ address │ │ minimum_order_amount │ │ rating │ └────────┬───────────────────┬────┘ │ 1 │ 1 │ │ │ ∞ │ ∞ ┌────────┴──────────┐ ┌────┴──────────────────────────┐ │ MENU_ITEMS │ │ CUSTOMER_ORDERS │ ├───────────────────┤ ├───────────────────────────────┤ │ PK id (IDENTITY) │ │ PK id (IDENTITY) │ │ FK restaurant_id │ │ FK restaurant_id │ │ name │ │ customer_name │ │ category │ │ delivery_address │ │ price │ │ items_summary │ │ available │ │ subtotal │ └───────────────────┘ │ delivery_fee │ │ total_amount │ │ status │ └───────────────────────────────┘


---

## 📡 REST API Endpoints

### 1. Restaurants API (`/api/restaurants`)
| Method | Endpoint | Description | Status |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/restaurants` | List all restaurants | `200 OK` |
| **GET** | `/api/restaurants/{id}` | Get restaurant by ID | `200 OK`, `404 Not Found` |
| **GET** | `/api/restaurants/search?cuisine={c}` | Case-insensitive search by cuisine | `200 OK` |
| **POST** | `/api/restaurants` | Register restaurant (validates rating & minimum) | `201 Created`, `400 Bad Request` |
| **PUT** | `/api/restaurants/{id}` | Update restaurant profile | `200 OK`, `404 Not Found` |
| **DELETE** | `/api/restaurants/{id}` | Remove restaurant | `204 No Content`, `404 Not Found` |

### 2. Menu Items API (`/api/menu-items`)
| Method | Endpoint | Description | Status |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/menu-items` | Add dish to restaurant using `MenuItemRequest` DTO | `201 Created`, `400 Bad Request` |
| **GET** | `/api/menu-items/restaurant/{id}` | View full menu of a restaurant | `200 OK` |
| **GET** | `/api/menu-items/category?category={c}` | Filter items by category (e.g. Burger, Beverage) | `200 OK` |
| **DELETE** | `/api/menu-items/{id}` | Delete menu item | `204 No Content`, `404 Not Found` |

### 3. Orders API (`/api/orders`)
| Method | Endpoint | Description | Status |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/orders` | Place order with dynamic fee & minimum check | `201 Created`, `400 Bad Request` |
| **GET** | `/api/orders` | List all customer orders | `200 OK` |
| **GET** | `/api/orders/{id}` | Track order by ID | `200 OK`, `404 Not Found` |
| **GET** | `/api/orders/restaurant/{id}` | View orders for a specific restaurant | `200 OK` |
| **GET** | `/api/orders/status?status={s}` | Filter orders by status | `200 OK` |
| **PUT** | `/api/orders/{id}?status={s}` | Update order status (PREPARING ➡️ ON_THE_WAY) | `200 OK` |

---

## 💡 Business Logic Highlights
1. **Minimum Order Spend**: Rejects orders that don't reach the restaurant's required minimum with a clear `400 Bad Request`.
2. **Tiered Delivery Calculation**: Orders \$30+ qualify for **Free Delivery (\$0.00)**; otherwise a standard **\$3.50** fee applies.
3. **Data Transfer Objects (DTO)**: `MenuItemRequest` and `OrderRequest` decouple network inputs from entity representations.
4. **Resilient Exception Interception**: `@ExceptionHandler` converts business errors into structured client responses without 500 crashes.

---

## 🛠️ Getting Started

### Prerequisites
- **Java**: Adoptium JDK 11+
- **Maven**: 3.6+

### Run Locally
```bash
git clone https://github.com/tayats/Project_QuickBite_API.git
cd Project_QuickBite_API
mvn spring-boot:run

API Base URL: http://localhost:8080
H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:quickbitedb, user: sa)