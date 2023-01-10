# ```Find your rentmate!``` service

## Local project setup

### Prerequisites

- [PostgreSQL 14.6](https://postgresapp.com/downloads.html)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Steps for running Spring Boot application

- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server by one of the following methods:
    - execute `brew services start postgresql@14`
    - use [Postgres GUI application](https://postgresapp.com/downloads.html)
- execute `mvn clean install`
- locally create a database called `fyrm` owned by user `postgres`
- start `maildev` mail server by executing `docker run -p 1080:1080 -p 1025:1025 maildev/maildev`
- go to http://0.0.0.0:1080 to see the emails
- start Spring Boot Application

### Run application in Docker

- generate application JAR file: `./mvnw clean package -DskipTests`
- move generated JAR file under `/src/main/docker/`
- move to Docker configuration directory: `cd src/main/docker/`
- start containers: `docker-compose up`
- stop containers: `docker-compose down`

### Steps for running tests

- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server by one of the following methods:
    - execute `brew services start postgresql@14`
    - use [Postgres GUI application](https://postgresapp.com/downloads.html)
- execute `mvn clean install`
- run tests

### Useful commands

- stop Docker container: `docker stop <container_id>`

### Troubleshooting

**Issue:** package `generatedapi` is not found  
**Solution:**

- go to `pom.xml` file located under project root
- open the Maven menu from the right side of the screen
- click `Reload All Maven Projects`
