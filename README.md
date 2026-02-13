# 🛒 eCommerce Backend API

A production-oriented backend REST API built with Java 17 and Spring Boot 3,
demonstrating secure JWT authentication with refresh tokens, clean modular architecture,
PostgreSQL with Flyway migrations, Dockerized deployment,
and CI/CD using GitHub Actions — all following real-world backend development practices.

---

## 🚀 TL;DR — Why this project?
This project shows how I design and build a **production-ready backend API**:

- 🔐 JWT authentication with refresh tokens
- 🧩 Clean modular & layered architecture
- 🐳 Dockerized deployment (same image for local & prod)
- 🔄 CI/CD with GitHub Actions
- ☁️ Real AWS deployment (EC2 + S3)

---

## 🔗 Live Demo
The application is live and running on **AWS EC2**.

**Swagger UI:**  
http://98.93.69.49:8080/swagger-ui/index.html

**Demo users:**
- Admin → `admin@gmail.com` / `admin1234`
- User → `user@gmail.com` / `user1234`

---

## 📌 Project Overview
This backend REST API simulates a real-world **eCommerce system**, focusing on core backend responsibilities such as:

- Authentication & authorization
- Clean separation of concerns
- Data persistence & migrations
- Infrastructure & deployment

UI complexity is intentionally minimized to emphasize **backend engineering quality**.

---

## 🧰 Tech Stack

### ☕ Java 17
Modern LTS Java version widely used in enterprise backends.

### 🌱 Spring Boot 3.x
REST API framework (controllers, DI, validation, configuration profiles).

### 🔐 Spring Security + JWT
- Stateless authentication
- Access & refresh token separation
- Custom JWT filter and token service

### 🗄 PostgreSQL + Spring Data JPA
- Relational database with strong entity relationships
- JPA Auditing (createdAt, updatedAt, createdBy, updatedBy)

### 🛫 Flyway
Versioned SQL migrations for controlled schema evolution.

### 🐳 Docker & Docker Compose
- Multi-stage Dockerfile (build → runtime)
- Local orchestration of API + PostgreSQL

### 🔄 GitHub Actions (CI/CD)
On push to `main`:
- Build project
- Build Docker image
- Push to Docker Hub (`<username>/ecommerce:latest`)

### ☁️ AWS (EC2 + S3)
- Application runs as a Docker container on EC2
- Amazon S3 used for product images and attachments

### ⚡ Performance & Optimization
- Pagination implemented for list endpoints
- Optimized JPA queries
- Indexing applied to frequently queried fields

---

## 🏗 Architecture Design

The project follows a **modular + layered architecture**:

`Controller → Service → Repository → Database`


### Layers
- **Controller** — request handling & validation
- **Service** — business logic
- **Repository** — data access
- **DTO** — request / response models

### Cross-cutting concerns
Located in the `common` package:
- Security
- Exception handling
- Auditing
- Configuration
- Utilities

---

## 🗂 Project Structure

```text
src/main/java/com/ecommerce/ecommerce
├── common
│   ├── security    # JWT, filters, security config
│   ├── exception   # Centralized error handling
│   ├── audit       # JPA auditing
│   ├── storage     # S3 abstraction
│   └── config, utils, components
└── modules
    ├── auth        # Login, register, refresh, logout
    ├── user        # Users & roles
    ├── product     # Products, categories, attachments
    ├── order       # Orders & order items
    ├── inventory   # Inventory transactions
    └── cart        # Shopping cart (in progress)
```
### Each module contains:
- `controller / service / repository / entity / dto / mapper`

## 📦 Implemented Modules

### 🔐 Auth (`/api/v1/auth`)
- `POST /login` — issue access & refresh tokens
- `POST /register` — create user account
- `POST /refresh` — renew access token
- `POST /logout` — invalidate refresh token

### 📦 Products & Categories
- CRUD operations
- Search functionality
- File attachments via Amazon S3

### 🧾 Orders
- Create orders & order items
- Update order status
- Fetch user-specific orders

### 📊 Inventory
- Inventory in transactions
- Stock tracking
- Inventory history

---

## 🔐 Security & Data Integrity
- JWT-based stateless authentication
- Access & refresh token separation
- Role-based authorization (ADMIN, USER)
- Method-level security with `@PreAuthorize`
- Flyway-managed schema migrations
- JPA Auditing

---

## 🔍 API Documentation

Swagger/OpenAPI is enabled.

- **Local:** http://localhost:8080/swagger-ui/index.html
- **AWS:** http://98.93.69.49:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://98.93.69.49:8080/v3/api-docs

Swagger allows:
- Inspecting request/response schemas
- Authenticating via JWT
- Testing real endpoints

---

## 🔄 CI/CD

Workflow: `.github/workflows/deploy.yml`

On push to `main` (excluding README changes):
- Build project
- Build Docker image
- Push image to Docker Hub

Secrets used:
- `DOCKER_HUB_USERNAME`
- `DOCKER_HUB_ACCESS_TOKEN`

---

## 🔐 Environment Variables

Environment-based configuration is used for:
- Database credentials
- JWT secrets
- AWS S3 credentials
- Demo user passwords

---

## ▶️ Running the Application

```bash
  docker compose up -d
```
## 🗺 Roadmap

### 🔐 Authorization Improvements
- Fine-grained permission model (beyond roles)
- Method-level authorization refinement

### 🧪 Testing
- Integration tests for core modules (auth, product, order)
- Security and authorization tests
- Service and repository layer test coverage

### ⚙️ Business Logic Enhancements
- Order lifecycle improvements (statuses & transitions)
- Stock validation during order creation
- Transactional consistency between order and inventory

### ☁️ Infrastructure & Deployment
- AWS environment hardening
- Improved logging and monitoring
- Environment-specific configuration enhancements (dev / prod)

### 🧹 Code Quality & Maintenance
- Refactoring of large service methods
- Improved exception hierarchy
- API response standardization

---

## 👤 Author

**Bekzod Asanov**  
Java Backend Developer

- GitHub: https://github.com/asanov-b
- LinkedIn: https://www.linkedin.com/in/bekzod-asanov