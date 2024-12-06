# Spring Boot Todo List API with Caching

A REST API for managing todos with MySQL as primary database and H2 as a caching layer.

## Prerequisites

- Java 23.0.1 or higher
- Maven 3.9.9 or higher
- MySQL
- IntelliJ IDEA (Community Edition is sufficient)

## Installation & Setup

### 1. MySQL Setup

```bash
# Install MySQL using Homebrew
brew install mysql

# Start MySQL Service
brew services start mysql

# Verify MySQL is running
brew services list

# Connect to MySQL
mysql -u root

# Create database
CREATE DATABASE tododb;
```

### 2. Project Setup

1. Generate project from [Spring Initializr](https://start.spring.io/) with these settings:
    - Project: Maven
    - Language: Java
    - Spring Boot: 3.3.6
    - Group: com.example.todo
    - Artifact: todo
    - Packaging: Jar
    - Java: 23

2. Add these dependencies:
    - Spring Web
    - Spring Data JPA
    - MySQL Driver
    - H2 Database

### 3. Configuration

Add the following to `src/main/resources/application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/tododb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# H2 Cache Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.second-datasource.url=jdbc:h2:mem:cachodb
spring.second-datasource.driverClassName=org.h2.Driver
spring.second-datasource.username=sa
spring.second-datasource.password=

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Project Structure

```
todo
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── todo
│       │               ├── TodoApplication.java
│       │               ├── controller
│       │               │   └── TodoController.java
│       │               ├── model
│       │               │   └── Todo.java
│       │               ├── repository
│       │               │   └── TodoRepository.java
│       │               └── service
│       │                   ├── TodoService.java
│       │                   └── CacheService.java
│       └── resources
│           └── application.properties
```

## API Endpoints

### Get all todos
```http
GET /api/todos
```

### Create a todo
```http
POST /api/todos
Content-Type: application/json

{
    "title": "Learn Spring Boot",
    "completed": false
}
```

### Get a specific todo
```http
GET /api/todos/{id}
```

### Update a todo
```http
PUT /api/todos/{id}
Content-Type: application/json

{
    "title": "Learn Spring Boot",
    "completed": true
}
```

### Delete a todo
```http
DELETE /api/todos/{id}
```

## Testing

### Using cURL

1. Create a todo:
```bash
curl -X POST http://localhost:8080/api/todos \
-H "Content-Type: application/json" \
-d '{"title":"Learn Spring Boot","completed":false}'
```

2. Get all todos:
```bash
curl http://localhost:8080/api/todos
```

3. Get a specific todo:
```bash
curl http://localhost:8080/api/todos/1
```

4. Update a todo:
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
-H "Content-Type: application/json" \
-d '{"title":"Learn Spring Boot","completed":true}'
```

5. Delete a todo:
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

### Using Postman

1. Import the following cURL commands into Postman
2. Create a new collection called "Todo API"
3. Test each endpoint with the appropriate HTTP method and payload

## Features

- CRUD operations for todos
- Soft deletion (todos are marked as deleted rather than physically removed)
- Two-layer caching with H2 database
- Proper error handling for deleted todos
- MySQL as primary database for persistence

## Response Status Codes

- 200: Success
- 201: Created
- 404: Not Found
- 410: Gone (when accessing deleted todo)
- 500: Server Error

## Troubleshooting

1. MySQL Connection Issues:
```bash
brew services restart mysql
```

2. Port Already in Use:
   Change the port in application.properties:
```properties
server.port=8081
```

3. Database Access Issues:
```sql
mysql -u root
GRANT ALL PRIVILEGES ON tododb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.