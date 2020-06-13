<!-- [![Build Status](https://travis-ci.com/givanthak/spring-boot-rest-api-tutorial.svg?branch=master)](https://travis-ci.com/givanthak/spring-boot-rest-api-tutorial)
[![Known Vulnerabilities](https://snyk.io/test/github/givanthak/spring-boot-rest-api-tutorial/badge.svg)](https://snyk.io/test/github/givanthak/spring-boot-rest-api-tutorial) -->

# Demo (Maker - Checker App) REST API with Spring Boot, Mysql, JPA and Hibernate

## Steps to Setup

**1. Clone the application**

```bash
https://gitlab.com/longbridge-tech/green-demo.git
```

```bash
http://localhost:8080/swagger-ui.html
```

**2. Create Mysql database**

```bash
create database maker_checker_db
```

**3. Change mysql username and password as per your installation**

- open `src/main/resources/application.properties`

- change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

- change `spring.datasource.initialization-mode=never` to `spring.datasource.initialization-mode=always` you only do this at first run after change it back

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/spring-boot-rest-api-tutorial-0.0.1-SNAPSHOT.jar

```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

<http://localhost:8080/swagger-ui.html>

Visit http://localhost:8080/api/v1/authentication/authenticate

to authenticate. Using the default ROLE_ADMIN username: greendublin007 and password: password
Project using JWT for authentication only swagger ui and http://localhost:8080/api/v1/authentication/authenticate are accessable with authentication.

Need help contact developer dublin-green

<greendublin007@gmail.com>
