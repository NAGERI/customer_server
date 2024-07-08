# Banker Application

This is a sample Spring Boot REST API application showcasing basic CRUD operations.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- [Java Development Kit (JDK)](https://adoptium.net/): Version 8 or higher.
- [Apache Maven](https://maven.apache.org/): To build and manage the project.
- [Git](https://git-scm.com/): To clone the repository.

## Getting Started

Follow these steps to run the Spring Boot REST API locally:

1. Clone the repository:

    ```bash
   git clone https://github.com/NAGERI/customer_server.git
   ```

2. Navigate to the project directory:

    ```bash
    cd banker
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/banker-1.0.0.jar
    ```

5. The application will start, and you can access the API at [http://localhost:8080](http://localhost:8080).

Equally, you can open the application with Intelij or Netbeans, install the dependencies and run the main class (BankerApplication.java)
## API Documentation

The API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui.html). You can use this page to explore and test the available endpoints.

## Configuration

You can configure the application by modifying the `src/main/resources/application.properties` file located in the `src/main/resources` directory.

