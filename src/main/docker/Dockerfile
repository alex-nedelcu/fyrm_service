FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
EXPOSE 1025
ENTRYPOINT ["java", "-jar", "application.jar"]