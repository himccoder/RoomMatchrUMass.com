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

## Steps to install mysql and run it as a local server (only one person has to do this)

### For mac

    1. brew install mysql
    2. brew services start mysql

### For Windows

1. Would need to install from browser and setup

### To test you installed it successfully:

    - mysql -u root
    - If it shows mysql>, then you have installed correctly. There shouldn't be any root password. If you have setup a root password, then use:
    - mysql -u root -p (if you have a root password)

### Update application.properties:

    spring.datasource.url=jdbc:mysql://DeviceIP:3306/roommate_matchr_db
    spring.datasource.username=roommate_user
    spring.datasource.password=roommate_pass
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### Turn off your firewall

- Turn off your firewall from settings

### For macs:

    Create this file in root of OS
    sudo nano /opt/homebrew/etc/my.cnf
    Paste:
    [mysqld]
    bind-address = 0.0.0.0
    port = 3306
    Restart mysql:
    brew services restart mysql

### For windows:

    1. Open PowerShell as Administrator

    2. Open MySQL config file (edit as Administrator)
    notepad "C:\ProgramData\MySQL\MySQL Server 8.0\my.ini"

    3. Inside my.ini, find:
        bind-address=127.0.0.1
        and change it to:
        bind-address=0.0.0.0
        port=3306
        Then SAVE the file.

    4. Restart MySQL Service
    net stop MySQL80
    net start MySQL80

### Log into MySQL as root

    mysql -u root -p

### Inside mysql, this allows others to connect.

    DROP USER IF EXISTS 'roommate_user'@'%';

    -- 2. Re-create user with correct password and remote access
    CREATE USER 'roommate_user'@'%' IDENTIFIED BY 'roommate_pass';

    -- 3. Grant DB access
    GRANT ALL PRIVILEGES ON roommate_matchr_db.* TO 'roommate_user'@'%';

    -- 4. Apply changes
    FLUSH PRIVILEGES;

### To test: This public ip must be set in your application.properties.

    mysql -h YOUR_PUBLIC_IP -u roommate_user -p roommate_matchr_db
