# coffeeshop-api

REST API for coffeeshop.

A RESTful API created using Spring Boot. I have used Mysql as the relational database and Spring data JPA to interact with that.
Apart from this, i have used JSON Web Token (JWT) for authentication and authorization users. Using JWT, we can protect certain endpoints and ensure that user must be authenticated to access those.

Database schema :

![db schema]()


## Setup and Installation

1. **Clone the repo from GitHub**
   ```sh
   git clone https://github.com/fazriridwan19/coffeeshop-api.git
   cd coffeeshop-api.git
   ```
2. **Spin-up Mysql database instance**

   You can use either of the below 2 options:
   - one way is to download from [here](https://www.postgresql.org/download) and install locally on the machine
   - another option is by running a postgres docker container:
     ```sh
     docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d -p 5432:5432 postgres
     ```
3. **Create database objects**
   Create schema with name **coffeshop**
     ```
4. **Update database configurations in application.properties**
   
   If your database is hosted at some cloud platform or if you have modified the SQL script file with some different username and password, update the src/main/resources/application.properties file accordingly:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:3306/coffeeshop
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
5. **Run the spring boot application**
   ```sh
   ./mvnw spring-boot:run
   ```
   this runs at port 8080 and hence all enpoints can be accessed starting from http://localhost:8080
