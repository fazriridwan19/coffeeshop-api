# coffeeshop-api

REST API for coffeeshop.

A RESTful API created using Spring Boot. I have used Mysql as the relational database and Spring data JPA to interact with that.
Apart from this, i have used JSON Web Token (JWT) for authentication and authorization users. Using JWT, we can protect certain endpoints and ensure that user must be authenticated to access those.

Database schema :

![db schema](https://github.com/fazriridwan19/coffeeshop-api/blob/main/download.png)


## Setup and Installation

1. **Clone the repo from GitHub**

   ```sh
   git clone https://github.com/fazriridwan19/coffeeshop-api.git
   cd coffeeshop-api
   ```
2. **Create database objects**

   Create schema with name **coffeshop**
3. **Update database configurations in application.properties**
   
   If your database is hosted at some cloud platform or if you have modified the SQL script file with some different username and password, update the src/main/resources/application.properties file accordingly:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/coffeshop
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
4. **Run the spring boot application**
   ```sh
   ./mvnw spring-boot:run
   ```
   this runs at port 8080 and hence all enpoints can be accessed starting from http://localhost:8080
