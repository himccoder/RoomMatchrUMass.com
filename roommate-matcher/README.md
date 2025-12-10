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

**Failure Responses**

```json
{
  "success": false,
  "message": "Email must be a valid umass.edu address"
}
```

```json
{
  "success": false,
  "message": "User with this email already exists. Login instead."
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

**Failure Response**

```json
{
  "success": false,
  "message": "Invalid credentials"
}
```

### 3. Submit User Survey

**Method:** POST  
**URL:** `/api/survey/submit`

**Request Body**

```json
{
  "userId": 1,
  "sleepSchedule": "EARLY_BIRD",
  "foodType": "VEGETARIAN",
  "personalityType": "INTROVERT",
  "petsPreference": "OKAY_WITH_PETS",
  "smokingPreference": "PREFER_NON_SMOKER_ONLY"
}
```

Note: To see all possible options that request body can take, please refer to: roommate-matcher/src/main/java/com/cs520group8/roommatematcher/model

**Success Response**

```json
{
  "success": true,
  "message": "Survey submitted successfully."
}
```

**Failure Response**

```json
{
  "success": false,
  "message": "Survey already submitted by this user."
}
```

## Steps to Integrate SQL server

### macOS

Install MySQL using below command:

```bash
brew install mysql
```

Start the server using below command:

```bash
brew services start mysql
```

To connect to server locally, use below command:

```bash
mysql -u root
```

A user needs to be set up with permissions for this application. To do so, follow the below commands:

```sql
CREATE USER 'roommate_user'@'localhost' IDENTIFIED BY 'roommate_pass';

GRANT ALL PRIVILEGES ON roommate_matchr_db.* TO 'roommate_user'@'localhost';

FLUSH PRIVILEGES;
```

To create the database for the application, use below command:

```sql
CREATE DATABASE roommate_matchr_db;
```

The tables will be created when the application starts up automatically.
