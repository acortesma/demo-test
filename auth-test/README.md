# ReadMe

## Introduction

Spring Boot service that connects to the service available in dockerHub
from [here](https://hub.docker.com/r/skeet15/auth-vivelibre) to get a valid token

## Feature

This service only exposes an endpoint and works as a gateway to format the response it gets from the
service providing the authentication token.

## API REST

| Resource  | Method | Path       | Descripción                    
|-----------|--------|------------|--------------------------------|
| Get token | GET    | /get-token | Obtain token with date request |

## Build

This project is built using the Maven tool. If you want to run all phases,
just run from the root of the project

````shell script
mvn clean install
````

To build a local docker image of the service you can run the command

````shell script
mvn clean package jib:dockerBuild
````

### Running locally

First you must start the token provider service, you can use the test-compose.yml included in the
project:

````shell script
docker-compose -f src/test/resources/test-compose.yml up
````

The project can be run from the IDE or by executing the following command:

````shell script
mvn spring-boot:run
````

### Running in Docker container

The project has a `docker-compose-app.yml` file to run the complete application.

First complete the correct variables in the file `docker-compose-app.yml`.

````
      - AUTH_USERNAME=***
      - AUTH_PASS=***
````

Start the two services by running the docker composer

````shell script
docker-compose -f docker-compose-app.yml up
````

