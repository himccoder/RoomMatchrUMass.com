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

## 1️⃣ Install MySQL

### macOS

```bash
brew install mysql
brew services start mysql
# Assuming you havent setup a password, should work the first time you install mysql
mysql -u root

Do these commands inside mysql
-- 1. DROP USER IF EXISTS 'roommate_user'@'%';

-- 2. Re-create user with correct password and remote access
CREATE USER 'roommate_user'@'%' IDENTIFIED BY 'roommate_pass';

-- 3. Grant DB access
GRANT ALL PRIVILEGES ON roommate_matchr_db.* TO 'roommate_user'@'%';

-- 4. Apply changes
FLUSH PRIVILEGES;

```
