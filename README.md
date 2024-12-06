# Spring Boot Todo List API with Caching

A REST API for managing todos with MySQL as primary database and H2 as a caching layer. The API supports CRUD operations with a two-layer caching system.

## Getting Started

### Prerequisites

- Java 23 or higher
- Maven 3.9.x or higher
- MySQL

### Installation & Setup

1. Clone the repository
```bash
git clone https://github.com/proSamik/Spring-Boot-Todo-List-API-with-Caching.git
cd Spring-Boot-Todo-List-API-with-Caching
```

2. Create MySQL database
```bash
mysql -u root
CREATE DATABASE tododb;
```

3. Configure MySQL connection in `src/main/resources/application.properties` if needed
```properties
spring.datasource.username=root
spring.datasource.password=your_password  # Leave empty if no password
```

4. Build the project
```bash
mvn clean install
```

5. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## Testing the API

### Create a Todo
```bash
curl -X POST http://localhost:8080/api/todos \
-H "Content-Type: application/json" \
-d '{"title":"Learn Spring Boot","completed":false}'
```

### Get all Todos
```bash
curl http://localhost:8080/api/todos
```

### Get a specific Todo
```bash
curl http://localhost:8080/api/todos/1
```

### Update a Todo
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
-H "Content-Type: application/json" \
-d '{"title":"Learn Spring Boot","completed":true}'
```

### Delete a Todo
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## Troubleshooting

1. If MySQL connection fails:
   - Verify MySQL is running: `brew services list`
   - Restart MySQL: `brew services restart mysql`

2. If port 8080 is already in use:
   - Change the port in `application.properties`:
     ```properties
     server.port=8081
     ```

## License

This project is licensed under the MIT License.
