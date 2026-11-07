# CargoXpress

CargoXpress is a Spring Boot web application for managing package shipments. Customers can submit and track deliveries, couriers can accept and update delivery jobs, and administrators can manage the people and shipment data used by the application.

![CargoXpress warehouse](src/main/resources/static/images/bl.jpg)

## Features

- **Customer**: create a shipment with package dimensions, weight, cost, origin, and destination; view shipment history.
- **Courier**: browse pending shipments, accept a job, and update its delivery status.
- **Administrator**: manage customers, couriers, packages, routes, and shipments.
- **Role-based pages**: customer, courier, and administrator dashboards and workflows use the logged-in session role.
- **Server-rendered UI**: Thymeleaf templates with project CSS, JavaScript, and image assets.

Shipment statuses used by the workflows are `PENDING`, `ACCEPTED`, `IN TRANSIT`, and `DELIVERED`.

## Technology

- Java 17
- Spring Boot 3.5.7
- Spring MVC and Thymeleaf
- Spring Data JPA / Hibernate
- MySQL
- Maven Wrapper

## Requirements

- JDK 17 or newer
- MySQL 8.x

The project includes the Maven Wrapper, so a separate Maven installation is not required.

## Run locally

1. Create the database in MySQL:

   ```sql
   CREATE DATABASE cargoxpress;
   ```

2. Check the datasource settings in [`src/main/resources/application.properties`](src/main/resources/application.properties). The defaults are:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cargoxpress
   spring.datasource.username=root
   spring.datasource.password=
   ```

   Change the username and password to match your local MySQL setup.

3. From the `cargoxspringboot` project directory, start the application on Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. Open [http://localhost:8099/home](http://localhost:8099/home). The login page is available at [http://localhost:8099/login](http://localhost:8099/login).

Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`, which creates or updates tables in the existing database during development. The application does not include pre-seeded accounts; register an account through the relevant signup page before logging in.

## Build and test

Run the test suite:

```powershell
.\mvnw.cmd test
```

Build an executable JAR:

```powershell
.\mvnw.cmd clean package
```

Run the packaged application:

```powershell
java -jar target\cargoxspringboot-0.0.1-SNAPSHOT.jar
```

## Project structure

```text
src/
├── main/
│   ├── java/com/example/cargoxspringboot/
│   │   ├── controller/   # Web routes and request handling
│   │   ├── entity/       # JPA entities for people, packages, routes, shipments
│   │   ├── repository/   # Spring Data repositories
│   │   └── service/      # Application and persistence operations
│   └── resources/
│       ├── static/       # CSS, JavaScript, and images
│       ├── templates/    # Thymeleaf pages
│       └── application.properties
└── test/                 # Spring Boot tests
```

## Notes

- MySQL must be running and the `cargoxpress` database must exist before startup.
- The default server port is `8099`.
- The current database settings and automatic schema updates are intended for local development. Use environment-specific credentials and a managed migration strategy before deploying to production.