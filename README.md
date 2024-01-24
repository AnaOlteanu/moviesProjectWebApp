# Movies management web application with Spring Framework
Master's project designed to showcase the implementation of a web application using the Spring MVC framework that provides functionalities regarding movies and other related details, such as actors and movie genres. 
## Technologies used
- **Spring Framework**
    - **Core Spring** for dependency injection and IoC
    - **Spring MVC** for web development
    - **Spring Data JPA** for data access and validation
    - **Spring Security** for user authentication and role-based access control
    - **Slf4j** for logs
    - Unit testing using **Mockito** (service and controller layer)
    - Integration testing using **MockMVC** (repository layer and web layer)
- **Thymeleaf**
    - template engine for front-end development
- **MySQL**
    - relational database for data storage
    - MySQL Workbench as the administration tool for the database
## Features
1. **Movie management**
   - attributes: _title, release date, genres, actors, description, movie type, length, image_
  - CRUD operations for movies
  - sorting by release date
  - list movies by genre
2. **Actor management**
    - attributes: _first name, last name, date of birth, gender_
  - CRUD operations for actors
  - search actor by name
3. **Genre management**
    - attributes: _name_
  - CRUD operations for genres
4. **User management**
    - attributes: _username, password_
  - register and authentication of a user on the platform by username and password
  - password secured using the BCrypt algorithm
  - role-based access control for different user types: admin or guest

