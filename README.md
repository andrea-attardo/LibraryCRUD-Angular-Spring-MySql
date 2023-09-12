# LibraryCRUD-Angular-Spring-MySql
A simple Library Application using CRUD operations with 3-tier architecture (frontend, backend and relational database). 

## Stack:
```
Angular 16 
Spring Boot
MySql
```

### Overview Frontend:
1) Each Book has id, title, category, availability.
2) We can create, retrieve, update, delete Books.
3) There is a Search bar for finding Books by title.

<img src="https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql/blob/main/biblioteca1.png?raw=true" width="500" >
<img src="https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql/blob/main/biblioteca2.png?raw=true" width="500" >

#### Rest API contract:
```
Methods	    Urls	                    Actions
POST        /api/books	              create new Book
GET         /api/books	              retrieve all Books
GET         /api/books/:id	      retrieve a Book by :id
PUT         /api/books/:id	      update a Book by :id
DELETE      /api/books/:id	      delete a Book by :id
DELETE      /api/books	              delete all Books
GET         /api/books?title=[keyword]	find all Books which title contains keyword
```


### To run frontend on local:

1) Install Angular 16: https://kinsta.com/knowledgebase/install-angular/
2) ```git clone https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql.git```
3) ```cd librarycrud-frontend```
4) ```ng serve --port 8081```


## Overview Backend:
Built a Rest CRUD API using Spring Boot, Spring Data JPA, Hibernate, Maven to interact with MySQL.
<img src="https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql/blob/main/project_structure.png?raw=true" width="500" >

## Setup a local Database:
```
install on local

Under src/main/resources folder, open application.properties and write these lines.

spring.datasource.url= jdbc:mysql://localhost:3306/testdb?useSSL=false
spring.datasource.username= root
spring.datasource.password= 123456

# for Spring Boot 2
# spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect

# for Spring Boot 3
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update
```
