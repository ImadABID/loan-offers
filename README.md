# loan-offers
A demo back-end application with a minimalistic front-end to showcase how Keycloak can be leveraged to secure a REST API.

## Technical Stack
This application is implemented using Spring Boot 3, with: 
- Spring Web to expose the api endpoints,
- Spring Security to secure the application by interacting with Keycloak,
- Spring Data to seamlessly use the JPA (with the Hibernate implementation)
- Flyway for database migration
- Postgres as a database driver
- H2 to have an in-memory database for unit tests


## Application Launch

Inside this git repository, launch the the following command in order the external services that this app depend on:

    docker compose up -d

Wait for about 1 minute to allow the containers to be fully functional and then launch the application using your favorite IDE or maven:

    mvn spring-boot:run

The application will be listening at the port: 8081.

Once you finished testing the app, stops the external service by launching this command:

    docker compose down