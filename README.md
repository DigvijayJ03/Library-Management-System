# Library Management System

## Overview

A console-based Library Management System developed using Core Java, JDBC, and MySQL.

The application manages books, users, transactions, fines, authentication, and reporting using a layered architecture.

## Features

### Book Management

* Add Book
* View Books
* Search Book by ID
* Search Book by Title
* Search Book by Author
* Delete Book
* Sort Books by Title
* Sort Books by Quantity

### User Management

* Add User
* View Users

### Transaction Management

* Issue Book
* Return Book
* Transaction History
* SQL JOIN Reports

### Fine Management

* Due Date Tracking
* Overdue Book Detection
* Fine Calculation

### Dashboard

* Total Books
* Total Users
* Issued Books
* Available Books
* Overdue Books

### Authentication

* Admin Login
* Librarian Login
* Role-Based Access

## Technologies Used

* Java
* JDBC
* MySQL
* Collections Framework
* Streams API
* Repository Pattern
* SQL Joins
* Exception Handling

## Project Structure

src/
├── model/
├── repository/
├── service/
├── util/
├── exception/
└── main/

## How to Run

1. Install MySQL
2. Create database and tables
3. Update credentials in DBConnection.java
4. Run Main.java

## Author

Digvijay
