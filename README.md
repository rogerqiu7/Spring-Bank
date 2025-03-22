# Spring Bank

Simple Spring Boot application with JPA and MySQL DB, using RESTful APIs to create, read, update, and delete account information.

## Technologies Used

- Spring Boot
- Spring Data JPA
- MySQL database
- Java 21

## Project Structure

```
spring-bank/
├── src/
│   ├── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── spring_bank/
│       │               └── SavingsController.java
│       │               └── Savings.java
│       │               └── SavingsRepository.java (interface only)
│       │               └── SavingsService.java
│       │               └── SpringBankApplication.java
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

## Running the Application

1. Clone the repository
2. Download and install latest MySQL database to local computer: https://dev.mysql.com/downloads/mysql/
3. Navigate to the project directory (update the MySQL password in the application.properties file)
4. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
5. The application will start on port 8080: http://localhost:8080/api/savings

## API Endpoints

- `GET /api/savings` - Get all accounts
- `GET /api/savings/{id}` - Get account by ID
- `GET /api/savings/email/{email}` - Get account by email
- `POST /api/savings` - Create a new account
- `PUT /api/savings/{id}` - Update an account
- `DELETE /api/savings/{id}` - Delete an account

## Example curl Commands

### Create a new account
```bash
curl -X POST http://localhost:8080/api/savings \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Test","lastName":"Person","email":"test.person@example.com","savingsAmount":1000.00}'
  
curl -X POST http://localhost:8080/api/savings \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Roger","lastName":"Qiu","email":"roger.qiu@example.com","savingsAmount":100000000.00}'
```

### Get all accounts
```bash
curl -X GET http://localhost:8080/api/savings
```

### Get account by ID
```bash
curl -X GET http://localhost:8080/api/savings/1
```

### Update account
```bash
curl -X PUT http://localhost:8080/api/savings/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","savingsAmount":2000.00}'
```

### Delete account
```bash
curl -X DELETE http://localhost:8080/api/savings/1
```

### Check results in database
```md
rogerqiu@Rogers-Air ~/documents/code/spring-bank % mysql -u root -p

Enter password:

mysql> SHOW DATABASES;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| spring_bank        |
| sys                |
+--------------------+

mysql> use spring_bank;
Database changed

mysql> show tables;
+-----------------------+
| Tables_in_spring_bank |
+-----------------------+
| savings               |
+-----------------------+

mysql> select * from savings;
+----+----------------------+------------+-----------+----------------+
| id | email                | first_name | last_name | savings_amount |
+----+----------------------+------------+-----------+----------------+
|  1 | john.doe@example.com | John       | Doe       |        1000.00 |
+----+----------------------+------------+-----------+----------------+
```

## How Data Flows Through the Application

```md
Client (Browser / Curl / Frontend)
            │
            ▼
────────────────────────────────────
🌐 Controller Layer (SavingsController.java)
- Handles HTTP requests (GET, POST, etc.)
- Maps URLs like /api/savings
- Sends/receives JSON
────────────────────────────────────
            │
            ▼
🧠 Service Layer (SavingsService.java)
- Contains business logic
- Coordinates between controller and repository
- Handles validation, transformation, etc.
────────────────────────────────────
            │
            ▼
🧲 Repository Layer (SavingsRepository.java)
- Talks to the database
- Uses JPA to save, update, delete, and find entities
────────────────────────────────────
            │
            ▼
🗃️ Database (MySQL)
- Stores savings accounts data in the 'savings' table
- Can be auto-generated via JPA or manually defined with schema.sql
────────────────────────────────────


```

1. Client sends HTTP request (e.g., GET /api/savings/1)
2. Controller receives the request
   - Maps URL to a controller method
3. Controller calls service method
   - e.g., service.getAccountById(1)
4. Service executes business logic
   - Validates inputs if needed
   - Calls repository methods
5. Repository accesses database
   - Spring Data JPA translates method calls into SQL queries
   - It automatically generates and executes an SQL query like:
   - SELECT * FROM savings WHERE id = 1
   - The database returns the result
6. Data flows back up the chain
   - Repository returns a Savings object
   - Service may apply additional logic
   - Controller converts the result to JSON
   - HTTP response is sent back to client

## Spring Boot Architecture

### Maven file (pom.xml)
- Declares project dependencies, such as Spring Boot, JPA, MySQL Driver.
- Tells Maven how to build and package the application.
- Automatically pulls all required libraries from the internet (like Gradle but for Maven).

### Application Config (application.properties)
- Contains settings for how the app connects to the database, what port it runs on, and how Hibernate behaves.
- Example: It defines the MySQL URL, username, password, and enables SQL logging.
- Controls automatic schema creation or whether to use a custom SQL script.

### Schema file (schema.sql)
- Optional SQL file that manually defines your database tables and structure.
- Spring Boot will run this at startup if you tell it to (e.g., when not using ddl-auto=update).
- Gives you full control over your schema instead of letting Spring auto-generate it from @Entity classes.

### Application Class (SpringBankApplication.java)
- The class with the main() method — this is where the app starts running.
- @SpringBootApplication bootstraps the entire app and enables:
- @Configuration: Defines beans (Java-managed components).
- @EnableAutoConfiguration: Lets Spring autoconfigure beans based on dependencies.
- @ComponentScan: Scans your project for @Component, @Service, @Repository, and @Controller classes to register.

### Model Layer (Savings.java)
- Represents the data structure and maps directly to a table in the database.
- @Entity: Tells Spring this class should be saved to a DB table.
- @Id + @GeneratedValue: Auto-generates the unique ID for each account.
- Contains fields like firstName, email, savingsAmount and their getters/setters.

### Repository Layer (SavingsRepository.java)
- Handles all interactions with the database.
- Extends JpaRepository to get built-in CRUD methods like save(), findById(), deleteById() — no SQL needed.
- Custom query methods (e.g., findByEmail()) are auto-implemented based on naming conventions.
- Example: findByEmail("john@example.com") runs SELECT * FROM savings WHERE email = 'john@example.com'.

### Service Layer (SavingsService.java)
- Contains the business logic of your app — processes data before saving or returning it.
- Sits between the controller and repository layers.
- Can handle things like validation, transactions, or calculations.
- Marked with @Service so Spring knows to manage it as a service component.
- Uses @Autowired to inject the repository.

### Controller Layer (SavingsController.java)
- Handles incoming HTTP requests and sends back JSON responses.
- Each method maps to a REST endpoint (e.g., GET /api/savings/{id}).
- Uses annotations like:
- @RestController: Combines @Controller and @ResponseBody for JSON APIs.
- @RequestMapping: Sets base path like /api/savings.
- @GetMapping, @PostMapping, etc.: Define specific HTTP routes and methods.

## Advantages of Using JPA over JDBC

- **Less Boilerplate Code**: No need to write SQL queries manually
- **Automatic Mapping**: JPA automatically maps entity classes to database tables
- **Repository Pattern**: Built-in repository methods for common operations
- **Type Safety**: JPA repositories provide type-safe methods
- **Error Handling**: Better exception translation