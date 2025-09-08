# Vendi - E-commerce

## 📌 About the project
Vendi is a personal development project I created to practice and deepen my skills in Java (Spring Boot), Vue.js, Docker, PostgreSQL, and other modern technologies.
The main goal of this project is to learn by building—applying theory in a real-world scenario, improving my full-stack development abilities, and gaining practical experience with tools used in professional environments.

---

## 🚀 Tech Stack

### 🖥️ **Frontend**
- [Vue.js 3](https://vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- [Vuetify](https://vuetifyjs.com/)
- [Axios](https://axios-http.com/) 
- [Vite](https://vitejs.dev/)

### 🛠️ **Backend**
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security) (Authentication/Authorization)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) (Database management)
- [PostgreSQL](https://www.postgresql.org/) (Relational database)
- [JWT](https://jwt.io/) (Authentication token)
- [Testcontainers](https://testcontainers.com/) (For database tests setup)
- [JUnit](https://junit.org/) (Testing)
- [Prometheus](https://prometheus.io/) + [Grafana](https://grafana.com/) (Observability & Monitoring)

---

## 🎯 Functionalities

📦 Product Management (Full CRUD)

    Users can create, read, update, and delete products.

    Each product includes fields such as name, description, price, image, and stock.

    Admin panel includes tools to manage all products in the database.

👥 User Roles and Permissions

    General users can view products and manage their own orders.

    Admin users can manage all products, users, and orders.

🛒 Shopping Cart with Total Calculation

    Users can add and remove products from the cart.

    Real-time cart updates with quantity and subtotal per item.

    Automatically calculates total price (including taxes, if applicable).

🔑 Authentication and Authorization (JWT + Spring Security)

    JWT-based authentication for secure and stateless login.

    Passwords encrypted with BCrypt.

    Role-based access control to restrict admin functionalities.

📜 User Registration and Login

    New users can sign up with email and password.

    Login system generates and returns a JWT token.

    Validation and error feedback on both frontend and backend.

💳 Payment Integration (Stripe/PayPal) (Still in progress)

    Payment gateway integration using Stripe and/or PayPal sandbox.

    Checkout flow includes summary, address, and secure payment.

    Orders are only confirmed after successful payment processing.

📊 Admin Dashboard for Order and Customer Management (Still in progress)

    Visual dashboard with metrics (total sales, number of users, pending orders).

    Admin can view and update order status (e.g., processing, shipped, delivered).

    Customer management: see user details and purchase history.

---

## 🛠️ How to run the project

### 🔹 1. Close the repository
```sh
  git clone https://github.com/Miguel-Pezzini/vendi.git
  cd vendi
```

### 🔹 2. Configure the database
``` sh
  docker-compose up -d
```

> **Note**: This will start the PostgreSQL container defined in the `docker-compose.yml` file.

### 🔹 3. Configure the backend (Spring Boot)
```sh
  cd vendi_backend
  mvn clean install
  mvn spring-boot:run
```
> **Note**: Make sure to configure your database credentials in the `application.properties` file before running.


### 🔹 4. Configure the frontend (Vue 3 + Vuetify)
```sh
  cd vendi_frontend
  npm ci
  npm run dev
```

URL local access: `http://localhost:3333`

---

## 🔗 API Endpoints

### 📌 **Authentication**
- `POST /auth/register` → User register
- `POST /auth/login` → Login and JWT token generation

### 📌 **Products**
- `GET /api/products` → List all products
- `GET /api/products/{id}` → Details of a product
- `POST /api/products` → Create a product
- `PUT /api/products/{id}` → Update a product
- `DELETE /api/products/{id}` → Delete a product

### 📌 **Cart**
- `POST /api/cart/add` → Add item to cart
- `GET /api/cart` → List all cart items
- `DELETE /api/cart/remove/{id}` → Remove item from cart

- ### 📌 **Category**
- `POST /api/category` → Create a category 
- `GET /api/category` → List all categories
---


## 🛡️ Security and Authentication
**Vendi** uses **JWT (JSON Web Token)** for authentication. To access protected routes, include the JWT token in the request header:
```sh
Authorization: Bearer <jwt-token>
```
