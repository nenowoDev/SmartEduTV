# SmartEduTV - Program Management System

> REST API - Spring Boot Application

---

## Table of Contents

- [Project Description](#project-description)
- [Application Domain](#application-domain)
- [Five Functionalities](#five-functionalities)
- [REST API Endpoints](#rest-api-endpoints)
- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Setup & Run](#setup--run)
- [Testing with Postman](#testing-with-postman)

---

## Project Description

**SmartEduTV** is a backend program management system built with **Java Spring Boot**. It allows educational TV administrators to create, manage, track, and search for broadcast programs through a fully RESTful API.

The system follows a clean **4-layer architecture** (Controller → Service → Repository → Entity) and uses **Spring Data JPA / Hibernate** for persistence. All API responses are returned in structured **JSON format** and are testable via **Postman**.

---

## Application Domain

**Educational Television Program Management**

An administrator can manage broadcast programs for an educational TV platform. Each program record holds details such as the program name, broadcast date, location, target participation count, description, approval status, and report availability.

---

## Five Functionalities

| # | Functionality | HTTP Method | Endpoint |
|---|--------------|-------------|----------|
| 1 | **Create** a new program | `POST` | `/api/programs` |
| 2 | **Retrieve all** programs or search by name | `GET` | `/api/programs` or `/api/programs?name=Math` |
| 3 | **Retrieve** a program by ID | `GET` | `/api/programs/{id}` |
| 4 | **Update** an existing program | `PUT` | `/api/programs/{id}` |
| 5 | **Delete** a program | `DELETE` | `/api/programs/{id}` |

---

## REST API Endpoints

### Base URL
```
http://localhost:8080/api/programs
```

---

### 1. Create a Program
**`POST /api/programs`**

**Request Body (JSON):**
```json
{
  "programName": "Science Today",
  "programDate": "2026-07-15",
  "programLocation": "Studio A",
  "targetParticipation": 200,
  "programDescription": "A weekly science broadcast for students.",
  "status": "Pending",
  "hasReport": false
}
```

**Response - `201 Created`:**
```json
{
  "status": "success",
  "message": "Program created successfully",
  "data": {
    "id": 1,
    "programName": "Science Today",
    "programDate": "2026-07-15",
    "programLocation": "Studio A",
    "targetParticipation": 200,
    "programDescription": "A weekly science broadcast for students.",
    "status": "Pending",
    "hasReport": false
  }
}
```

---

### 2. Retrieve All Programs
**`GET /api/programs`**

**Response - `200 OK`:**
```json
{
  "status": "success",
  "message": "3 program(s) retrieved successfully",
  "count": 3,
  "data": [ { ... }, { ... }, { ... } ]
}
```

### Search by Name
**`GET /api/programs?name=Science`**

**Response - `200 OK`:**
```json
{
  "status": "success",
  "message": "1 program(s) found matching: Science",
  "count": 1,
  "data": [ { ... } ]
}
```

---

### 3. Retrieve Program by ID
**`GET /api/programs/{id}`**

**Response - `200 OK`:**
```json
{
  "status": "success",
  "message": "Program retrieved successfully",
  "data": {
    "id": 1,
    "programName": "Science Today",
    ...
  }
}
```

**Response - `404 Not Found`:**
```json
{
  "status": "error",
  "message": "Program not found with id: 99"
}
```

---

### 4. Update a Program
**`PUT /api/programs/{id}`**

**Request Body (JSON):** *(same fields as Create)*

**Response - `200 OK`:**
```json
{
  "status": "success",
  "message": "Program updated successfully",
  "data": { ... }
}
```

---

### 5. Delete a Program
**`DELETE /api/programs/{id}`**

**Response - `204 No Content`:** *(empty body - REST convention)*

**Response - `404 Not Found`:**
```json
{
  "status": "error",
  "message": "Program not found with id: 99"
}
```

---

## Project Structure

```
SmartEduTV/
├── src/
│   └── main/
│       ├── java/com/featureforce/smartedutv/
│       │   ├── SmartedutvApplication.java       ← Entry point
│       │   ├── config/
│       │   │   └── SecurityConfig.java          ← Spring Security config (CSRF disabled for API)
│       │   ├── controller/
│       │   │   └── ManageProgramController.java ← REST API layer (@RestController)
│       │   ├── entity/
│       │   │   └── ManageProgram.java           ← JPA entity / DB table model
│       │   ├── repository/
│       │   │   └── ManageProgramDao.java        ← Data access layer (Hibernate Session)
│       │   └── service/
│       │       └── ManageProgramService.java    ← Business logic layer (@Service)
│       └── resources/
│           └── application.properties           ← App configuration (JPA, JSON, server)
├── pom.xml                                      ← Maven dependencies
└── README.md
```

### Layer Responsibilities

| Layer | Class | Role |
|-------|-------|------|
| **Controller** | `ManageProgramController` | Receives HTTP requests, returns JSON responses |
| **Service** | `ManageProgramService` | Contains business logic and validation rules |
| **Repository** | `ManageProgramDao` | Handles database read/write via Hibernate |
| **Entity** | `ManageProgram` | Maps Java class to `program` database table |

---

## Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Programming language |
| Spring Boot | 4.1.0 | Application framework |
| Spring Web MVC | - | REST API (`@RestController`) |
| Spring Data JPA | - | ORM / database persistence |
| Hibernate | - | JPA implementation (Session API) |
| H2 Database | - | In-memory database (no installation needed) |
| Spring Security | - | API security configuration |
| Jackson | - | JSON serialization / deserialization |
| Lombok | - | Reduces boilerplate code |
| Maven | - | Build and dependency management |

---

## Prerequisites

Before running this project, ensure you have the following installed:

- **Java JDK 21** or later - [Download](https://adoptium.net/)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **Git** - [Download](https://git-scm.com/)
- **Postman** - [Download](https://www.postman.com/downloads/) *(for API testing)*
- An IDE such as **IntelliJ IDEA**, **Eclipse**, or **VS Code** with Java extensions

---

## Setup & Run

### Step 1 - Clone the Repository

```bash
git clone https://github.com/<your-username>/SmartEduTV.git
cd SmartEduTV
```

### Step 2 - Build the Project

```bash
mvn clean install
```

### Step 3 - Run the Application

```bash
mvn spring-boot:run
```

Or run the main class directly from your IDE:
```
com.featureforce.smartedutv.SmartedutvApplication
```

### Step 4 - Verify the Application is Running

Open your browser and navigate to:
```
http://localhost:8080/api/programs
```

You should receive a JSON response:
```json
{
  "status": "success",
  "message": "No programs available",
  "count": 0,
  "data": []
}
```

> **Note:** The database is in-memory. All data is cleared when the application stops. This is expected behaviour for development and demonstration purposes.

---

## Testing with Postman

Import the following requests into Postman to test all five functionalities.

### Setup Headers (apply to all requests)
| Key | Value |
|-----|-------|
| `Content-Type` | `application/json` |

---

### Request Collection

| # | Method | URL | Description |
|---|--------|-----|-------------|
| 1 | `POST` | `http://localhost:8080/api/programs` | Create a new program |
| 2 | `GET` | `http://localhost:8080/api/programs` | Retrieve all programs |
| 2b | `GET` | `http://localhost:8080/api/programs?name=Science` | Search programs by name |
| 3 | `GET` | `http://localhost:8080/api/programs/1` | Retrieve program by ID |
| 4 | `PUT` | `http://localhost:8080/api/programs/1` | Update program by ID |
| 5 | `DELETE` | `http://localhost:8080/api/programs/1` | Delete program by ID |

### Sample POST Body
Copy this into Postman → Body → raw → JSON:
```json
{
  "programName": "Science Today",
  "programDate": "2026-07-15",
  "programLocation": "Studio A",
  "targetParticipation": 200,
  "programDescription": "A weekly science broadcast for students.",
  "status": "Pending",
  "hasReport": false
}
```

### Expected HTTP Status Codes

| Operation | Success Code | Error Code |
|-----------|-------------|------------|
| Create (POST) | `201 Created` | - |
| Retrieve (GET) | `200 OK` | `404 Not Found` |
| Update (PUT) | `200 OK` | `404 Not Found` |
| Delete (DELETE) | `204 No Content` | `404 Not Found` |

