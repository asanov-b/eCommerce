# 🛒 eCommerce Backend API

A production-oriented backend application built with **Java 17** and **Spring Boot**.  
The project includes 🔐 **JWT authentication with refresh tokens**, 🧩 **modular architecture**, 🗄 **PostgreSQL with
Flyway migrations**, 🐳 **Docker & Docker Compose**, and 🔄 **CI/CD using GitHub Actions**.

The goal of this project is to demonstrate clean, scalable backend design following real-world development practices.

## 📌 Project Overview

This project is a backend REST API designed to simulate a real-world eCommerce system.  
It focuses on core backend responsibilities such as authentication, authorization, data persistence,
and infrastructure concerns rather than UI complexity.

The application is built using a **modular and layered architecture**, where each business domain
is separated into its own module. This approach improves readability, maintainability,
and scalability as the project grows.

Key backend concerns addressed in this project include:

- 🔐 Secure authentication and authorization
- 🧩 Clear separation of concerns (controller, service, repository)
- 🗄 Reliable data storage and migrations
- 🐳 Containerized runtime environment
- 🔄 Automated build and delivery pipeline

This project is intended as a **portfolio-grade backend application**,
demonstrating practical skills used in production environments.

## 🧰 Tech Stack

### Backend

- ☕ **Java 17** — LTS version used for stability and modern language features
- 🌱 **Spring Boot** — core framework for building RESTful REST APIs
- 🔐 **Spring Security** — authentication and authorization
- 🪪 **JWT (Access & Refresh Tokens)** — stateless authentication with token rotation

### Data & Persistence

- 🗄 **PostgreSQL** — primary relational database
- 🧬 **Spring Data JPA** — ORM and repository abstraction
- 🗃 **Flyway** — versioned database schema migrations

### Cloud & Storage

- ☁️ **AWS (EC2)** — application hosting and deployment
- 🪣 **Amazon S3** — file and attachment storage

### Infrastructure

- 🐳 **Docker** — containerization of the backend application
- 🧩 **Docker Compose** — orchestration of application and database services

### CI/CD

- 🔄 **GitHub Actions** — automated build and delivery pipeline
- 📦 **Docker Hub** — container image registry

### Additional

- ✅ **Bean Validation (Jakarta Validation)** — request and data validation
- 📝 **Centralized Logging** — application-level logging and monitoring

---

## 🏗 Architecture Design

The project follows a modular, layered architecture:

- **Controller layer** — request handling and validation
- **Service layer** — business logic
- **Repository layer** — data access
- **DTO layer** — request/response models

Cross-cutting concerns such as security, auditing, and exception handling
are centralized under a shared `common` package.

---

## 🗂 Project Structure

- `src/main/java/com/ecommerce/ecommerce/common`
    - `security` — Spring Security configuration, filters, JWT utilities
    - `exception` — centralized exception handling
    - `audit` — JPA auditing (createdAt/updatedAt, createdBy/updatedBy)
    - `storage` — S3 integration layer
    - `config`, `utils`, `component`
- `src/main/java/com/ecommerce/ecommerce/modules`
    - `auth` — authentication & token flow
    - `user` — user management and roles
    - `product` — products + categories + attachments/files
    - `order` — order creation and management
    - `cart` — module skeleton (planned)
- `.github/workflows/` — CI/CD pipeline
- `docker-compose.yml`, `Dockerfile` — containerization & orchestration


## 📦 Implemented Modules

- 🔐 **Auth (`modules/auth`)** — login/registration, access & refresh token handling
- 👤 **User (`modules/user`)** — users and roles
- 📦 **Product (`modules/product`)**
    - Products CRUD
    - Categories (inside product module)
    - Attachments / files (inside product module)
- 🧾 **Order (`modules/order`)** — order creation and management

---

## 🛒 Planned Modules

- **Cart (`modules/cart`)** — currently a skeleton, will be implemented

---

## 🔐 Security

- JWT-based stateless authentication
- Access & refresh token separation
- Role-based authorization (`ADMIN`, `USER`)
- Custom security filter chain
- Method-level security with `@PreAuthorize`

---

## 🔄 Authentication Flow

1. User logs in with credentials
2. Access & refresh tokens are issued
3. Access token is used for API requests
4. Refresh token is used to obtain a new access token when expired

---

## 📘 API Documentation

- OpenAPI / Swagger UI is available for API exploration and testing

---

## 🗄 Database

- PostgreSQL as the primary relational database
- Proper entity relationships and constraints
- Pagination and filtering support

---

## 🧬 Database Migrations

- Flyway is used for versioned schema migrations
- Migrations are executed automatically on application startup

---

## ☁️ Cloud & Deployment

- Deployed on **AWS EC2**
- Dockerized application using Docker Compose
- File storage handled via **Amazon S3**

---

## 🕒 Auditing

- Automatic tracking of:
    - `createdAt`, `updatedAt`
    - `createdBy`, `updatedBy`
- Implemented using JPA Auditing and Spring Security context

---

## ✅ Validation

- Request-level validation using Jakarta Bean Validation
- Business-level validation inside service layer

---

## ⚠️ Error Handling

- Centralized exception handling with `@RestControllerAdvice`
- Unified error response structure

---

## 📝 Logging

- Application-level logging for monitoring and debugging
- Important business events are logged

---

## 🔄 CI/CD

- Automated pipeline using GitHub Actions
- Builds Docker image on each push to `main`
- Pushes image to Docker Hub

---

## 🔐 Environment Variables

- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `SPRING_PROFILES_ACTIVE`

---

## ▶️ Running the Application

### Using Docker Compose
```bash
docker compose up -d
```

## 🗺 Roadmap

The following items represent planned and ongoing improvements
to bring the project closer to a full production-ready eCommerce backend:

### 🛒 Shopping Cart
- Implement cart and cart item entities
- Add/update/remove cart items
- User-specific cart ownership
- Convert cart contents to an order

### 🔐 Authorization Improvements
- Fine-grained permission model (beyond roles)
- Ownership checks for user-specific resources
- Method-level authorization refinement

### 🧪 Testing
- Integration tests for core modules (auth, product, order)
- Security and authorization tests
- Repository and service layer test coverage

### ⚙️ Business Logic Enhancements
- Order lifecycle improvements (statuses, transitions)
- Stock validation during order creation
- Transactional consistency checks

### ☁️ Infrastructure & Deployment
- AWS environment hardening
- Environment-specific configurations (dev / prod)
- Improved logging and monitoring

### 🧹 Code Quality & Maintenance
- Refactoring of large service methods
- Improved exception hierarchy
- API response standardization

### 📈 Performance & Scalability
- Query optimization
- Pagination tuning
- Indexing strategy review

---

## 👤 Author

This project was developed as a **portfolio backend application**  
to demonstrate real-world backend development skills using modern Java
and Spring Boot technologies.

The project is actively maintained and continuously improved
to reflect production-level backend practices.

