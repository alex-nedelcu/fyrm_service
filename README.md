# ```Find your rentmate!``` service

## Local project setup

#### Prerequisites

- [PostgreSQL 14.6](https://postgresapp.com/downloads.html)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

#### Steps for running Spring Boot application
- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server by one of the following methods:
  - execute `brew services start postgresql@14`
  - use [Postgres GUI application](https://postgresapp.com/downloads.html)
- execute `mvn clean install`
- locally create a database called `fyrm` owned by user `postgres`
- start Spring Boot Application

#### Steps for running tests
- start [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- start PostgreSQL server by one of the following methods:
  - execute `brew services start postgresql@14`
  - use [Postgres GUI application](https://postgresapp.com/downloads.html)
- execute `mvn clean install`
- run tests

#### Troubleshooting
**Issue:** package `generatedapi` is not found  
**Solution:**
- go to `pom.xml` file located under project root
- open the Maven menu from the right side of the screen
- click `Reload All Maven Projects`
