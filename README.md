# E-Learning Platform API

This is a Spring Boot application that provides an API for an E-Learning Platform. It is built using Java 17+ and Spring Boot, and it utilizes Basic Authentication for securing endpoints. Flyway is used for database migrations, Lombok for reducing boilerplate code, and MySQL as the database. The project is built using Maven.

## Features

- **User Management**: Create operations for managing users (students, administrators).
- **Course Management**: Create and Get All courses. Courses can have multiple modules.
- **Module Management**: Manage modules within courses. Each module may contain lessons, quizzes, or assignments.
- **Test Management**: Students can submit their Quiz Answers and Assignments for their respective modules.
- **Enrollment**: Allow Students to enroll in courses.
- **Progress Tracking**: Track Student progress within courses and modules.
- **Authentication & Authorization**: Secure endpoints using Basic Authentication.

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Maven
- Flyway
- Lombok
- Swagger UI & OpenAPI

## Getting Started

1. **Clone the repository**:

   ```bash
   git clone git@github.com:agokulravikumar/elearning_server.git
   cd elearning-server
   ```

2. **Set up the database**:

    - Create a MySQL database and user with the help of script in `database.sql`
    - Update the `application.yml` file with the database credentials (If there is any change in password).


3. **Run the application**:

   You can run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**:

   Once the application is running, you can access the API endpoints using tools like Postman or curl. Basic Authentication is required, so make sure to include your username and password in the request headers.


5. **Create Test Users**:

   For development purposes, you can create test users using the following endpoint:

   ```http
   POST /api/users
   ```

   Include the user details in the request body. This endpoint is useful for creating sample users to test the functionality of the API.

## API Documentation

The API documentation will be available at `http://localhost:6161/api/api-docs.html` once the application is running. It provides detailed information about each endpoint, including request parameters and response formats.