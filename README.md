# Rate Limiter API in Spring Boot

This is a Spring Boot-based API application that tackle the issues that has a notification service by implementing
a rate limiting api, using a token bucket algorithm.

## Getting started

Pre-requisites:
Java 17 (check the version you have: java -version)
Maven 3.8.5 (check the version you have: mvn --version)

To run the app, clone this repository and execute the following command:

mvn spring-boot:run

Testing

http://localhost:8080/api/send-notification

Check allowRequest() in NotificationService L16