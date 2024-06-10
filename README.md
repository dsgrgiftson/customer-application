# Customer Application

This is an assignment for ABN Amro to create a simple customer application.

## Description

This application is a simple customer management system built with Java, SQL, Spring Boot, and Maven. It allows users to
register, log in, and manage their customer details.

## Technologies Used

- Java
- SQL
- Spring Boot
- Maven

## Unit Testing

The application includes unit tests for the `CustomerController` and `CustomerService` classes. These tests cover all
methods in the classes and consider all possible scenarios to achieve 100% coverage.

## How to Build

To build the application, you need to have Java and Maven installed on your system.

Build the application by following these steps:

1. Clone the repository ```git clone https://github.com/dsgrgiftson/customer-application.git```
2. Go to the project directory ```cd customer-application```
3. ```mvn clean install```

## How to Run

To run the application, you need to have Java, Maven and/or Docker installed on your system.

#### Run the application using maven spring boot plugin executing:

```mvn spring-boot:run```

#### Run the application as Docker container by following these steps after building the application:

1. Start Docker on your system
2. Build the Docker image using ```docker build -t customer-application .```
2. Run the Docker container using ```docker run -p 8080:8080 customer-application```

The application starts with a default port of 8080. You can access the application at http://localhost:8080. It is
loaded with a default H2 database.

## How to Test

The application is integrated with Swagger UI for testing the APIs and Postman collection is also included in the
repository for testing.

Proof of testing is included in the repository as screenshots too. Postman collection and screenshots are available
under /customer-application/postman.

There are multiple ways of testing this application,

1. Using Postman
    1. Import the Postman collection from the repository
    2. Run the collection
2. Using Swagger UI
    1. Go to http://localhost:8080/swagger-ui.html
    2. Test the APIs by selecting the API and clicking on "Try it out"
3. Using Junit Tests
   a. Run the tests using ```mvn test```

## Endpoints

 Endpoint                       | Type | Status Code | Description                                                                              
--------------------------------|------|-------------|------------------------------------------------------------------------------------------
 http://localhost:8080/register | POST | 200         | Successfully registered user                                                             
 http://localhost:8080/register | POST | 400         | Registration unsuccessful because of bad request - user already exists or missing fields 
 http://localhost:8080/register | POST | 500         | Registration unsuccessful because of an unexpected error                                 
 http://localhost:8080/logon    | GET  | 200         | Successfully logged in                                                                   
 http://localhost:8080/logon    | GET  | 401         | Unauthorized - Invalid credentials                                                       

## Future Improvements

1. The application can be containerized using Docker and deployed to Kubernetes for scaling and managing the
   application.
2. Integration tests were not part of this assignment as it mentioned only Junit Tests. They can be added to the project
   to test the application end-to-end.
3. Usage of Spring Security for securing the APIs.