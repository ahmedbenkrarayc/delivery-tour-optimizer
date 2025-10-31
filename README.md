# 🚚 Delivery Tour Optimization System

## 📦 Overview

The **Delivery Tour Optimization System** is a Spring Boot web application designed to help logistics companies **optimize their delivery routes** while managing vehicles, warehouses, and deliveries efficiently.  
It compares two algorithms — **Nearest Neighbor** and **Clarke & Wright Savings** — to find the best possible delivery tour based on distance, capacity, and other constraints.

---

## 🎯 Main Features

- 🚗 **Vehicle Fleet Management** (bike, van, truck)  
- 📍 **Delivery Management** with address, GPS coordinates, and time slots  
- 🗺️ **Automatic Tour Optimization** using:
  - Nearest Neighbor Algorithm  
  - Clarke & Wright Savings Algorithm  
- 📊 **Performance Comparison** between algorithms  
- 🧮 **Distance Calculation** using geographical coordinates  
- 🔁 **Manual Status Update** for deliveries (`PENDING`, `IN_TRANSIT`, `DELIVERED`, `FAILED`)  
- 🧱 **CRUD Operations** for all entities via REST API  
- 🧰 **Swagger UI** for API documentation  
- 🧪 **JUnit Tests** for service and algorithm layers  

---

## 🏗️ Project Architecture

```
src/
 ├── main/
 │   ├── java/com/logistics/delivery_optimizer/
 │   │   ├── controller/        # REST Controllers
 │   │   ├── service/           # Business logic
 │   │   ├── optimizer/         # Algorithms (NearestNeighbor, ClarkeWright)
 │   │   ├── repository/        # Data access layer
 │   │   ├── dto/               # Data Transfer Objects
 │   │   ├── mapper/            # Entity-DTO mappers
 │   │   └── model/             # Entities (Delivery, Vehicle, Tour, Warehouse)
 │   └── resources/
 │       ├── application.properties
 │       └── applicationContext.xml  # Manual dependency injection
 └── test/
     └── java/...                # Unit tests
```

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot (Java 17) |
| ORM | Spring Data JPA |
| Database | H2 (in-memory) |
| Build Tool | Maven |
| API Docs | Swagger / OpenAPI |
| Testing | JUnit 5 |
| Logging | SLF4J |
| Validation | Jakarta Validation |
| Utilities | Lombok, Spring DevTools, SonarLint |

> 💡 *All dependencies are injected via* `applicationContext.xml`.  
> *Annotations such as* `@Autowired`, `@Service`, `@Component`, *etc. are intentionally not used.*

---

## 🧮 Algorithms

### 🔹 Nearest Neighbor (NN)
A greedy algorithm that always selects the nearest unvisited delivery point.  
- Very fast (~50ms for 100 deliveries)  
- Often produces longer total distances

### 🔹 Clarke & Wright Savings (CW)
Optimizes routes by merging paths based on the greatest "savings" in distance.  
- Slightly slower (~200ms for 100 deliveries)  
- Reduces total distance by up to **28%**

---

## 🚀 Getting Started

### 1️⃣ Clone the repository
```bash
git clone https://github.com/ahmedbenkrarayc/delivery-tour-optimizer.git
cd delivery-tour-optimizer
```

### 2️⃣ Configure environment
Edit `src/main/resources/application.properties` to set up your port or H2 configurations if needed.

### 3️⃣ Run the project
```bash
mvn spring-boot:run
```

### 4️⃣ Access the API
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 📚 API Endpoints (Examples)

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/api/deliveries` | List all deliveries |
| `POST` | `/api/deliveries` | Create a new delivery |
| `GET` | `/api/tours/optimized/{vehicleId}` | Get optimized tour for a vehicle |
| `GET` | `/api/tours/distance/{tourId}` | Get total distance of a tour |
| `GET` | `/swagger-ui.html` | API documentation |

---

## 🚛 Vehicle Constraints

| Type | Max Weight | Max Volume | Max Deliveries |
|------|-------------|-------------|----------------|
| BIKE | 50 kg | 0.5 m³ | 15 |
| VAN | 1000 kg | 8 m³ | 50 |
| TRUCK | 5000 kg | 40 m³ | 100 |

---

## 📄 Class Diagram

<img width="1535" height="540" alt="image" src="https://github.com/user-attachments/assets/0b0c3381-c64d-4e7f-b87d-e7d10eb3e1be" />

---

## Project Structure

```
delivery-tour-optimizer/
│
├── .mvn/
│   └── wrapper/               ← Maven wrapper files (mvnw, mvnw.cmd, etc.)
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── <base package>/
│   │   │       ├── config/           ← configuration classes (Spring Boot config, beans)
│   │   │       ├── controller/       ← REST / web controllers
│   │   │       ├── model/            ← domain models/entities
│   │   │       ├── service/          ← business-logic / algorithm implementations
│   │   │       ├── repository/       ← data access classes (if present)
│   │   ├── resources/
│   │       ├── application.properties (or .yml) ← app configuration
│   │       └── static/ / templates/   ← static web assets / view templates (if web UI)
│   ├── test/
│       └── java/
│           └── <base package>/       ← unit/integration tests
│
├── .env.example                   ← Example environment config file
├── .gitignore
├── HELP.md                        ← Documentation / usage instructions
├── mvnw / mvnw.cmd                ← Maven wrapper scripts
├── pom.xml                        ← Maven project descriptor
└── README.md                      ← Project overview, description of algorithms (Nearest Neighbor, Clarke & Wright)

```

---

## 🧪 Testing

Run unit tests using Maven:
```bash
mvn test
```

Example tested components:
- Tour Optimization Service  
- Delivery Repository  
- Distance Calculator  
- DTO Mapper  

---

## 🐳 Optional Enhancements

- ✅ Integration tests  
- ✅ Liquibase database versioning  
- ✅ Docker containerization  
