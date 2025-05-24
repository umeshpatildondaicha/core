# Medical Application Core

A Spring Boot application for managing medical records, doctors, and patients with Keycloak integration for authentication and authorization.

## Features

- User authentication and authorization with Keycloak
- Doctor and Patient management
- Role-based access control
- API documentation with Swagger/OpenAPI
- Rate limiting
- Caching
- Health monitoring
- Prometheus metrics

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- Keycloak 22.0.5 or higher

## Environment Variables

Create a `.env` file in the project root with the following variables:

```env
DB_URL=jdbc:mysql://localhost:3306/medical_db
DB_USERNAME=root
DB_PASSWORD=root
KEYCLOAK_URL=http://localhost:8180
KEYCLOAK_REALM=medical-realm
KEYCLOAK_CLIENT_ID=medical-client
KEYCLOAK_CLIENT_SECRET=your-client-secret
KEYCLOAK_ADMIN_USERNAME=admin
KEYCLOAK_ADMIN_PASSWORD=admin
KEYCLOAK_ADMIN_CLIENT_ID=admin-cli
```

## Setup

1. Start MySQL:
```bash
docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0
```

2. Start Keycloak:
```bash
docker run --name keycloak -p 8180:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

3. Configure Keycloak:
   - Create a new realm named "medical-realm"
   - Create a new client named "medical-client"
   - Create roles: ROLE_ADMIN, ROLE_DOCTOR, ROLE_PATIENT
   - Create an admin user and assign the ROLE_ADMIN role

4. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

## API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI documentation: http://localhost:8080/api-docs

## Monitoring

The application exposes several monitoring endpoints:
- Health check: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics
- Prometheus: http://localhost:8080/actuator/prometheus

## Testing

Run the tests with:
```bash
mvn test
```

## Security

The application uses:
- Keycloak for authentication and authorization
- JWT tokens for stateless authentication
- Role-based access control
- Rate limiting to prevent abuse
- CORS configuration for frontend integration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details. 