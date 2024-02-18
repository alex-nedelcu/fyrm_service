# ```Roommates Matching Engine``` backend

### Frontend

Find frontend codebase [here](https://github.com/alex-nedelcu/fyrm_frontend).

### Prerequisites

- [PostgreSQL 14.6](https://postgresapp.com/downloads.html)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [maildev](https://github.com/maildev/maildev)

### Run application in local environment

- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server using [Postgres GUI application](https://postgresapp.com/downloads.html)
- `mvn clean install`
- locally create a database called `fyrm` owned by user `postgres`
- start `maildev` mail server:

```bash 
docker run -p 1080:1080 -p 1025:1025 maildev/maildev
```

- go to http://0.0.0.0:1080 to see the emails
- start Spring Boot Application

### Run application in Docker

- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- go to project root and generate application JAR file: `./mvnw clean package -DskipTests`
- move generated JAR file under `/src/main/docker/`
- move to Docker configuration directory: `cd src/main/docker/`
- delete previous application image: `docker image rm fyrm_service`
- start containers: `docker-compose up [-d]`
- stop containers: `docker-compose down`

### Run tests

- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server using [Postgres GUI application](https://postgresapp.com/downloads.html)
- `mvn clean install`
- `mvn clean test`

### Troubleshooting

**Issue:** package `generatedapi` is not found  
**Solution:**

- go to `pom.xml` file located under project root
- open the Maven menu from the right side of the screen
- click `Reload All Maven Projects`
