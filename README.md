# Car Rental System (Java + JDBC)

A console-based Car Rental System built using Java and JDBC with PostgreSQL.  
The system allows users to rent cars, return cars, search cars, and view total revenue.

## Features
- Rent a car
- Return a car
- View available cars
- Search cars by brand
- Admin login
- View total revenue

## Technologies Used
- Java
- JDBC
- PostgreSQL
- SQL

## Project Structure

Car-Rental-System-Java
│
├──src
│   └── vehicle
│       ├── Car.java
│       ├── CarRental.java
│       ├── Customer.java
│       ├── DBConnect.java
│       ├── Main.java
│       └── Rental.java
├── database
│   └── schema.sql
│
└── README.md


## How to Run

1.Clone the repository

  git clone https://github.com/yourusername/Car-Rental-System-Java-JDBC.git

2. Create the PostgreSQL database

   Ex: CREATE DATABASE car_db;

3. Run the SQL script in database/schema.sql

4. Update database credentials in

   DBConnect.java

5. Run
   Main.java

Sample Output


===== CAR RENTAL SYSTEM =====
1. Rent a Car
2. Return a Car
3. Show Available Cars
4. Search Car by Brand
5. Show Total Revenue
6. Exit

   
**Learning Outcomes**

--Object-Oriented Programming in Java

--JDBC database connectivity

--SQL query implementation

--Exception handling

--Input validation

## Author
Sneha Saldur
