# Roommate Matcher Backend (Spring Boot)

This is the backend REST API for the Roommate Matcher application. It is built using Spring Boot, it uses an in-memory H2 database for development and testing. SQL DB to be added soon.

## Getting Started

### Prerequisites
- Java 25
- Git installed
- No need to install Maven manually (project includes the Maven Wrapper `mvnw`)

### Running the Application

From the project root directory:

#### MacOS / Linux:
```bash
./mvnw spring-boot:run
```
### Windows
```bash
mvnw spring-boot:run
```
The application will start at: http://localhost:8080

## API Endpoints

### 1. Register User
**Method:** POST  
**URL:** `/api/auth/register`  

**Request Body**
```json
{
  "name": "john_doe",
  "email": "john@example.com",
  "password": "mypassword123"
}
```
**Success Response**
```json
{
    "success": true,
    "message": "User registered successfully"
}
```

### 2. Login User
**Method:** POST  
**URL:** `/api/auth/login`  

**Request Body**
```json
{
  "email": "john@example.com",
  "password": "mypassword123"
}
```
**Success Response**
```json
{
    "success": true,
    "message": "Login successful"
}
```