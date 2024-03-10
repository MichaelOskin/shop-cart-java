# shop-cart-java
A simple project to simulate the shopping cart of an online store and an authentication service

### admin run
![image](/screenshots/admin-run.png)


### user run
![image](/screenshots/user-run.png)


### Necessary dependencies
[![Java](https://img.shields.io/badge/-Java-orange?style=flat-square&logo=java)
](https://www.oracle.com/java/technologies/downloads/)[![Gradle](https://img.shields.io/badge/-Gradle-02303A?style=flat-square&logo=gradle)](https://gradle.org/install/)


## Building and launching a project
```sh
git clone https://github.com/MichaelOskin/shop-cart-java.git
cd shop-cart-java && chmod +x build.sh && ./build.sh
```

## Docker run
```sh
docker build -t shop-cart .
docker run -it shop-cart
```

## Initializing paths
Set the required project paths. To log in to the administrator, use the following `username&password`
```properties
database.sqlite.path=data.db
json.path=devices-latest.json
script.sql.path=db/init.sql
authentication.username=admin
authentication.password=Admin123
```

## Technologies used in the project
### 1. [sqlite-jdbc](https://github.com/xerial/sqlite-jdbc?tab=readme-ov-file)
>SQLite JDBC is a library for accessing and creating SQLite database files in Java
### 2. [java-faker](https://github.com/DiUS/java-faker)
>This library is a port of Ruby's faker gem (as well as Perl's Data::Faker library) that generates fake data
### 3. [jackson](https://github.com/FasterXML/jackson)
>Jackson has been known as "the Java JSON library" or "the best JSON parser for Java". Or simply as "JSON for Java"


## Functionality
### Sqlite database
![image](/screenshots/database.png)


### Features of implemented entities:
|          Administrator          |               User                | 
|:-------------------------------:|:---------------------------------:|
|    1. Executing SQL queries     |  1. Replenishment of the balance  |
|   2. Output of a data sample    | 2. Add/remove products from cart  |
| 3. Automatic creation of tables | 3. Buy&save shopping cart history |
|  4. Automatic data generation   |    4. View the product catalog    |
