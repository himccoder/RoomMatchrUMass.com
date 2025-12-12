# Roommate Matcher Backend (Spring Boot)

This is the backend REST API for the Roommate Matcher application. It is built using Spring Boot. It is integrated with MySQL server which serves as the database of the application. Below are the steps the run and deploy the application along with the database server.

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
