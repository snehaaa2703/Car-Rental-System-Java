CREATE TABLE car(
carid VARCHAR(10) PRIMARY KEY,
brand VARCHAR(50),
model VARCHAR(50),
baseprice DOUBLE PRECISION,
isavailable BOOLEAN
);

CREATE TABLE customers(
customer_id VARCHAR(20) PRIMARY KEY,
name VARCHAR(100)
);

CREATE TABLE rentals(
rental_id SERIAL PRIMARY KEY,
carid VARCHAR(10),
custid VARCHAR(20),
days INT,
return_date TIMESTAMP
);
