## OpenVaccine Vaccination Portal

A web-based vaccination management system developed for the **SE2030 Software Engineering** group assignment in the **second year, first semester**.

OpenVaccine supports the management of vaccination centers, vaccine inventory, appointments, dose administration, vaccination certificates, adverse reactions, and reports through a single portal.

## Project Overview

The system is designed to support the main operational activities of a vaccination program:

- Manage vaccine stock, batches, expiry dates, quantities, and storage locations.
- Register and manage vaccination centers and their facilities.
- Schedule and maintain patient vaccination appointments.
- Record administered doses and certificate information.
- Monitor and manage adverse reactions after vaccination.
- Produce vaccination-related reports and analytics.
- Protect authenticated application areas through login session handling.

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.1.0
- **Web layer:** Spring MVC and Thymeleaf
- **Persistence:** Spring Data JPA and Hibernate
- **Database:** MySQL 8.4
- **Build tool:** Maven Wrapper
- **Development environment:** Docker Compose
- **Frontend:** HTML, CSS, and Thymeleaf templates

## Project Structure

```text
src/
├── main/
│   ├── java/com/se2030/vaccination_portal/
│   │   ├── config/          # Login and web configuration
│   │   ├── controller/      # HTTP request handling
│   │   ├── model/           # JPA domain models
│   │   └── service/         # Application and business logic
│   └── resources/
│       ├── static/css/      # Stylesheets
│       ├── templates/       # Thymeleaf views
│       └── application.properties
└── test/
    └── java/                # Automated tests
```

## Main Modules

| Module | Purpose |
|---|---|
| Vaccine Stock and Inventory | Maintains vaccine names, batches, manufacturers, quantities, dates, and storage locations. |
| Vaccination Center and Facility Management | Maintains vaccination center details, capacity, operating hours, contact information, and status. |
| Vaccine Appointment and Scheduling | Registers patients and manages appointment dates, times, vaccine selections, centers, and appointment status. |
| Dose Administration Logs and Certification | Records administered doses and generates or stores vaccination certificate information. |
| Adverse Reaction Monitoring and Incident Management | Records post-vaccination reactions, severity, reporting dates, descriptions, and actions taken. |
| Reporting and Analytics Management | Provides vaccination-related reports and analytical information for operational review. |

## Requirements

Install the following before running the application:

- Java Development Kit (JDK) 21 or later
- Docker Desktop or Docker Engine with Docker Compose
- Git

Verify the installations:

```bash
java -version
./mvnw -version
docker compose version
```

On Windows, use `mvnw.cmd` instead of `./mvnw`.

## Setup and Installation

1. Clone the repository and enter the project directory.

   ```bash
   git clone <repository-url>
   cd vaccination-portal-mem1
   ```

2. Start the MySQL database container.

   ```bash
   docker compose up -d
   ```

   The database is available on port `3307` and uses the database name `vaccination_portal` for local development.

3. Start the Spring Boot application.

   ```bash
   ./mvnw spring-boot:run
   ```

4. Open the application in a browser:

   ```text
   http://localhost:8080
   ```

The application uses Hibernate's `update` mode, so the required tables are created or updated automatically when the application connects to the database.

## Default Local Database Configuration

The current development configuration uses the following local values:

| Setting | Value |
|---|---|
| Database host | `localhost` |
| Database port | `3307` |
| Database name | `vaccination_portal` |
| Database username | `root` |
| Database password | `root` |

These values are intended for local development only. Use environment variables or a secrets manager for production deployments.

## Application Access

The protected modules require a logged-in session. For the local demonstration setup, use the demo account configured by the project:

- Username: `admin`
- Password: `admin123`

After signing in, the dashboard provides access to the application modules. Log out when finished using the portal.

## Recommended Testing Order

Some records depend on data created in earlier modules. For the smoothest demonstration, use this order:

1. Sign in.
2. Create vaccine inventory records.
3. Create vaccination centers.
4. Create patient appointments.
5. Create dose administration records.
6. Record adverse reactions.
7. Review reports and analytics.

Detailed sample data and end-to-end testing steps are available in [TRY_OUT.md](TRY_OUT.md).

## Running Tests

Run the test suite with the Maven Wrapper:

```bash
./mvnw test
```

## Stopping the Database

Stop the MySQL container with:

```bash
docker compose down
```

To remove the database container and its persistent local data as well:

```bash
docker compose down -v
```

## Team Responsibilities

The following responsibility areas are assigned to the SE2030 group members. Student IDs are included for identification; personal email addresses and passwords are intentionally omitted.

| Student ID | Responsibility |
|---|---|
| IT25100152 | Dose Administration Logs and Certification System |
| IT25102368 | Vaccination Center and Facility Management |
| IT25100136 | Vaccine Appointment and Scheduling System |
| IT25102367 | Reporting and Analytics Management |
| IT25100125 | Adverse Reaction Monitoring and Incident Management |
| IT25102353 | Vaccine Stock and Inventory Management |

## Academic Context

This project was developed as a group assignment for the **SE2030 Software Engineering** module during the **second year, first semester**.
