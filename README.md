# Rate Limiter API in Spring Boot

This is a Spring Boot-based API application that tackle the issues that has a notification service by implementing
a rate limiting api, using a token bucket algorithm.

## Prerequisites

Before you begin, ensure you have met the following requirements:

Pre-requisites:
Java 8 or later
Maven 3.8.5

## Getting started

### Installation

1. Clone this repository:

```shell
git clone https://github.com/blandinekaneza/rate-limiter-api.git
```

2. To run the app execute the following command:

```shell
mvn spring-boot:run
```

### Testing

You can use Postman(an API platform for using APIs) or any other testing platform of your choice.
The bucket is reset after 1min.

```shell
endpont: http://localhost:8080/api/send-notification
method: POST

body:
{
    "subId": "I4AwaGSLSO" // from the subscribers.son file saved in resources
}
```