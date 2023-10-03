<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<br />
<div align="center">
  <a href="https://github.com/ssabarot/registration-app">
    <img src="registration-logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Registration Application</h3>

  <p align="center">
    Registration Application with REST api controller and CRUD methods.
  </p>
</div>


## About The Project

Here is a simple Spring Boot project with API that exposes 3 services :
- getAllUsers() to retrieve the list of users registered
- getUserById(id) to find a user by his id
- createUser(user) to create a new user

This app uses the following technologies :
- Java version 21
- Spring boot version 3
- H2 embedded database


## Prerequisites
- Java 21
- Maven
- Git
- Postman (collection file [here](https://github.com/ssabarot/registration-app/postman_collection.json))
- an IDE (for example IntelliJ or Eclipse)


## Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ssabarot/registration-app.git
   ```
2. Install dependencies, compile and run tests with maven
   ```sh
   mvn clean install
   ```
3. Launch the spring-boot application with maven
   ```js
   mvn spring-boot:run
   ```
4. Connect to H2 database with username at http://localhost:8080/users/h2-ui

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## API Description

Link to the Swagger UI documentation : http://localhost:8080/swagger-ui-api.html

Once the server is started, you can use the 3 following API :

- GET http://localhost:8080/api/users/{id} to retrieve a user by his id.
- GET http://localhost:8080/api/users to retrieve all users.
- POST http://localhost:8080/api/users to create a new user.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Project configuration

The configuration file is [application.properties](https://github.com/ssabarot/registration-app/src/main/resources/application.properties)
- Root context is /api/users
- Database is h2 in memory and url is jdbc:h2:mem:registrationdb
- Database console is enabled on /h2-ui

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[contributors-shield]: https://img.shields.io/github/contributors/ssabarot/registration-app.svg?style=for-the-badge
[contributors-url]: https://github.com/ssabarot/registration-app/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ssabarot/registration-app.svg?style=for-the-badge
[forks-url]: https://github.com/ssabarot/registration-app/network/members
[stars-shield]: https://img.shields.io/github/stars/ssabarot/registration-app.svg?style=for-the-badge
[stars-url]: https://github.com/ssabarot/registration-app/stargazers
[issues-shield]: https://img.shields.io/github/issues/ssabarot/registration-app.svg?style=for-the-badge
[issues-url]: https://github.com/ssabarot/registration-app/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/ssabarot
[product-screenshot]: images/screenshot.png
