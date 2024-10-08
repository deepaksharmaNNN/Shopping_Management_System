# Shopping Management System

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [Contact](#contact)

## Introduction

The Shopping Management System is a comprehensive e-commerce application developed using Java and Spring Boot. This project provides a RESTful API for managing various aspects of online shopping, including user management, product management, cart operations, order processing, and category management.

## Features

- **User Management:** Add, update, and delete regular and admin users.
- **Product Management:** Save and retrieve product information, including filtering by category and brand.
- **Cart Management:** Create and manage shopping carts, including adding, updating, and removing items.
- **Order Management:** Place, find, cancel, and check the status of orders.
- **Category Management:** Manage product categories with CRUD operations.
- **Error Handling:** Custom exceptions for better error handling and consistent API responses.
- **Input Validation:** Utilizing Jakarta Bean Validation for validating input data.

## Technologies Used

- **Java 21**
- **Spring Boot 3.3.3**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Lombok**
- **Jakarta Bean Validation**
- **Spring Security**
- **JUnit**
- **Spring Boot Starter Test**
- **Mockito**

## Usage

### API Endpoints

- **User Management**
  - **Add User:** `POST /api/v1/user/add/user`
  - **Add Admin User:** `POST /api/v1/user/add/admin`
  - **Get All Users:** `GET /api/v1/user/all`
  - **Delete User:** `DELETE /api/v1/user/delete/{id}`
  
- **Product Management**
  - **Save Product:** `POST /api/v1/product/save`
  - **Find Product by Name:** `GET /api/v1/product/find-by/name/{name}`
  - **Get All Products:** `GET /api/v1/product/all`

- **Cart Management**
  - **Create Cart:** `POST /api/v1/cart/create/{userId}`
  - **Get Cart:** `GET /api/v1/cart/get/{userId}`
  - **Add Item to Cart:** `POST /api/v1/cart/add`
  - **Update Item Quantity:** `PUT /api/v1/cart/update/quantity/{quantity}`
  
- **Order Management**
  - **Place Order:** `POST /api/v1/order/place`
  - **Get All Orders:** `GET /api/v1/order/all`
  - **Cancel Order:** `POST /api/v1/order/cancel`

- **Category Management**
  - **Add Category:** `POST /api/v1/category/add`
  - **Get All Categories:** `GET /api/v1/category/all`

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository.**
2. **Create a new branch:**
   ```bash
   git checkout -b feature-branch
3. **Commit your changes:**
    ```bash
    git commit -m "Add new feature"
4. **Push to the branch:**
    ```bash
    git push origin feature-branch
5. **Create a Pull Request.**

## Contact

For any queries, feel free to reach out:

- **Name:** Deepak Sharma
- **Email:** dsharma2828@gmail.com
- **GitHub:** [deepaksharmaNNN](https://github.com/deepaksharmaNNN)
