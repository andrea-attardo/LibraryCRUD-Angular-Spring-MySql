# LibraryCRUD-Angular-Spring-MySql
A simple Library Application using CRUD operations with 3-tier architecture (frontend, backend and relational database). 

## Stack:
```
Angular 16 
Spring Boot
MySql
```

## Overview Frontend:
1) Each Book has id, title, category, availability.
2) We can create, retrieve, update, delete Books.
3) There is a Search bar for finding Books by title.

<img src="https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql/blob/main/biblioteca1.png?raw=true" width="500" >
<img src="https://github.com/andrea-attardo/LibraryCRUD-Angular-Spring-MySql/blob/main/biblioteca2.png?raw=true" width="500" >

```
Rest API assumed

Methods	    Urls	                    Actions
POST        /api/books	              create new Book
GET         /api/books	              retrieve all Books
GET         /api/books/:id	      retrieve a Book by :id
PUT         /api/books/:id	      update a Book by :id
DELETE      /api/books/:id	      delete a Book by :id
DELETE      /api/books	              delete all Books
GET         /api/books?title=[keyword]	find all Books which title contains keyword
```

## Backend:


## Database:
