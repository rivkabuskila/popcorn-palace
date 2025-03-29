## Prerequisites

Ensure you have the following installed on your system:

- **JDK 21+**
- **Maven 3+**
- **Docker Compose**


## Run PostgreSQL Database with Docker Compose
docker compose up -d

## Build the Application
mvn clean install

## Run the Application
mvn spring-boot:run
Once the application is running, you can test the API using foe example:
- **curl -X GET http://localhost:8080/movies/all**
- **curl -X POST http://localhost:8080/movies -H "Content-Type: application/json" -d '{ "title": "Sample Movie Title", "genre": "Action", "duration": 120, "rating": 8.7, "releaseYear": 2025 }'**


## Running Tests
mvn test



