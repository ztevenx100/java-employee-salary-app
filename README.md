# Employee Salary Management Application

This Spring Boot application manages employee information and calculates annual salaries by consuming REST APIs. It features a responsive design and implements enterprise-level practices.

## Technology Stack

- Java 17
- Spring Boot
- Maven 3.6+
- Thymeleaf
- Bootstrap 5
- JUnit 5
- SLF4J for logging
- Jackson for JSON processing

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- IDE (IntelliJ IDEA, Eclipse, or NetBeans)
- Git

## Project Setup

### Clone the Repository
```bash
git clone https://github.com/ztevenx100/java-employee-salary-app
cd java-employee-salary-app
```

### IDE Setup

#### IntelliJ IDEA
1. Go to `File > Open`
2. Navigate to the project directory
3. Select `pom.xml` and open as project
4. Wait for Maven to import dependencies

#### Eclipse
1. Go to `File > Import`
2. Select `Existing Maven Projects`
3. Navigate to project directory
4. Select `pom.xml`
5. Click `Finish`

#### NetBeans
1. Go to `File > Open Project`
2. Navigate to project directory
3. Select the project
4. Click `Open Project`

## Build and Run

### Using Maven
```bash
# Clean and install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

### Using IDE
1. Find `EmployeeSalaryAppApplication.java`
2. Right-click and select `Run As > Java Application`

The application will start at `http://localhost:8080`

## Features

- View all employees
- Search employee by ID
- Calculate annual salaries
- Responsive Bootstrap UI
- RESTful API consumption
- Caching support
- Retry mechanism for API failures
- Error handling and validation

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/exam/employeeSalaryApp/
│   │       ├── controller/    # MVC Controllers
│   │       ├── model/         # Data models
│   │       ├── repository/    # Data access
│   │       └── service/       # Business logic
│   └── resources/
│       ├── static/           # Static resources
│       └── templates/        # Thymeleaf templates
└── test/
    └── java/                # Test classes
```

## API Endpoints

### Application Endpoints
- GET `/` - Home page with employee list
- GET `/?id={employeeId}` - Search by ID
- GET `/about` - About page

### Consumed External APIs
- GET `http://dummy.restapiexample.com/api/v1/employees` - Get all employees
- GET `http://dummy.restapiexample.com/api/v1/employee/{id}` - Get employee by ID

## Testing

Run tests using:
```bash
mvn test
```

The project includes comprehensive unit tests for:
- Controllers
- Services
- Repositories
- Models

## Deployment

### As WAR file
1. Generate WAR:
```bash
mvn clean package
```
2. Find the WAR file in `target/employeeSalaryApp-1.0-SNAPSHOT.war`
3. Deploy to your Java server (Wildfly, Tomcat, etc.)

### Using Spring Boot embedded server
```bash
java -jar target/employeeSalaryApp-1.0-SNAPSHOT.war
```

## Technical Features

- MVC Architecture with Spring Boot
- SOLID principles implementation
- Object-Oriented Programming practices
- Responsive design with Bootstrap
- Caching with Spring Cache
- Exponential backoff retry mechanism
- Comprehensive logging
- Exception handling
- JSON data processing with Jackson

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request