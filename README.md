# 🍕 QuickBite REST API

[![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.18-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT_Auth-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL_Cloud-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![JUnit 5](https://img.shields.io/badge/Testing-JUnit_5_%26_Mockito-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)

An enterprise-grade online food ordering and delivery RESTful microservice built with **Java 11**, **Spring Boot**, **Spring Security**, **JWT (JSON Web Tokens)**, **Spring Data JPA**, and a persistent cloud **PostgreSQL** database.

The platform strictly follows the **3-Tier Layered Architecture** with cryptographic authentication, Role-Based Access Control (RBAC), relational entity mapping (`@ManyToOne`), dynamic business rules, and a full automated test suite.

---

## 🏛️ System Architecture
[ Client / Web Browser / Mobile App ] │ ▼ (HTTP + Authorization: Bearer ) ┌─────────────────────────────────────────────────────────┐ │ 0. Security Layer (Spring Security + JJWT) │ │ • JwtAuthenticationFilter, BCryptPasswordEncoder │ │ • Role-Based Access Control (ROLE_ADMIN, ROLE_USER) │ └────────────────────────┬────────────────────────────────┘ │ ▼ (Authenticated Requests) ┌─────────────────────────────────────────────────────────┐ │ 1. Presentation Layer (@RestController) │ │ • AuthController, RestaurantController, │ │ MenuItemController, OrderController │ │ • Standardized ResponseEntity & Exception Handling │ └────────────────────────┬────────────────────────────────┘ │ ▼ (DTOs / Domain Models) ┌─────────────────────────────────────────────────────────┐ │ 2. Business Logic Layer (@Service) │ │ • RestaurantService, MenuItemService, OrderService │ │ • Minimum spend validation & Dynamic delivery fee │ └────────────────────────┬────────────────────────────────┘ │ ▼ (Entity Persistence) ┌─────────────────────────────────────────────────────────┐ │ 3. Data Access Layer (Spring Data JPA / @Repository) │ │ • UserRepository, RestaurantRepository, │ │ MenuItemRepository, OrderRepository │ │ • Derived queries without SQL boilerplate │ └────────────────────────┬────────────────────────────────┘ │ ▼ (SSL Connection over AWS) ┌─────────────────────────────────────────────────────────┐ │ 4. Persistence Layer (Cloud PostgreSQL Engine) │ │ • Tables: users, restaurants, menu_items, │ │ customer_orders │ └─────────────────────────────────────────────────────────┘

---

## 🔐 Security & RBAC Specifications

| Resource / Endpoint | HTTP Method | Access Level | Description |
| :--- | :--- | :--- | :--- |
| `/api/auth/**` | `POST` | **Public** | User registration & login with JWT generation |
| `/api/restaurants/**` | `GET` | **Public** | Public browsing of all restaurant menus |
| `/api/menu-items/**` | `GET` | **Public** | Public browsing of restaurant dishes |
| `/api/orders/**` | `POST`, `GET` | **Authenticated (`ROLE_USER` / `ROLE_ADMIN`)** | Placing and tracking customer orders |
| `/api/restaurants/**` | `POST`, `PUT`, `DELETE` | **`ROLE_ADMIN` Only** | Managing restaurant listings |
| `/api/menu-items/**` | `POST`, `DELETE` | **`ROLE_ADMIN` Only** | Managing dish catalog |

---

## 🧪 Automated Testing Suite

The application includes an automated test suite powered by **JUnit 5**, **Mockito**, and **MockMvc**:
- **`OrderServiceTest`**: Unit tests verifying dynamic fee tiers (\$0 vs \$3.50) and exception assertions (`assertThrows`) using mock repositories.
- **`RestaurantControllerTest`**: Controller integration tests asserting HTTP status codes (`200 OK`, `404 Not Found`) and validating JSON output via `jsonPath`.

Run all tests via Maven:
```bash
mvn test

📡 REST API Summary
Authentication (/api/auth)
POST /api/auth/register — Registers user with BCrypt password hashing.
POST /api/auth/login — Authenticates credentials and returns signed JWT token.
Restaurants (/api/restaurants)
GET /api/restaurants — List all restaurants.
GET /api/restaurants/{id} — Fetch restaurant by ID.
GET /api/restaurants/search?cuisine={c} — Search by cuisine type.
POST /api/restaurants — Create restaurant (Admin only).
DELETE /api/restaurants/{id} — Delete restaurant (Admin only).
Menu Items (/api/menu-items)
POST /api/menu-items — Add dish to menu (Admin only).
GET /api/menu-items/restaurant/{id} — View menu of a restaurant.
DELETE /api/menu-items/{id} — Delete dish (Admin only).
Orders (/api/orders)
POST /api/orders — Place order with minimum check and dynamic fee (Authenticated).
GET /api/orders — View order list (Authenticated).
PUT /api/orders/{id}?status={s} — Update status (PREPARING ➡️ ON_THE_WAY).