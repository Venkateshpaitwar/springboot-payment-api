# Spring Boot Payment API

A simple RESTful backend service built with **Spring Boot** demonstrating a layered architecture using **Controller, Service, Repository, DTO, and Entity** components.
The project simulates retrieving payment details based on a request object.

---

## Features

* Layered backend architecture
* DTO-based request and response handling
* Service layer for business logic
* Repository layer simulating database interaction
* Clean separation of concerns
* Maven-based Spring Boot project

---

## Project Architecture

Client Request → Controller → Service → Repository → Entity → Response DTO

The flow ensures proper separation between API handling, business logic, and data access.

---

## Project Structure

```
src/main/java/com/backendBegins/paymentapi

├── controller
│   └── PaymentController
│
├── service
│   └── PaymentService
│
├── repository
│   └── PaymentRepository
│
├── dto
│   ├── PaymentRequest
│   └── PaymentResponse
│
├── entity
│   └── PaymentEntity
│
└── PaymentApplication
```

---

## Tech Stack

* Java
* Spring Boot
* Maven
* Spring Web
* REST API

---

## How to Run

1. Clone the repository

```
git clone https://github.com/Venkateshpaitwar/springboot-payment-api.git
```

2. Navigate to the project directory

```
cd springboot-payment-api
```

3. Run the application

```
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```
---
## 📑 API Documentation

This project uses **Swagger (OpenAPI)** for interactive API documentation.

Swagger automatically generates API documentation from the Spring Boot controllers and DTOs.

### Access Swagger UI

After running the application, open:

http://localhost:8080/swagger-ui/index.html

### Features

- View all available API endpoints
- Inspect request and response models
- Execute API requests directly from the browser
- Explore validation rules and parameters

### Example Endpoint

POST /payments

Request Body:

{
"amount": 1500,
"currency": "INR",
"userEmail": "user@gmail.com"
}
---

## Future Improvements

* Integrate a real database using **Spring Data JPA**
* Add **CRUD APIs for payments**
* Implement **exception handling**
* Add **validation for request objects**
* Integrate **Swagger/OpenAPI documentation**
* Add **unit and integration tests**

---

## Learning Purpose

This project was built to understand:

* Spring Boot project structure
* Dependency injection
* DTO mapping
* Service and repository layers
* Backend API development fundamentals

---

## Author

**Venkatesh Paitwar**

GitHub:
https://github.com/Venkateshpaitwar
