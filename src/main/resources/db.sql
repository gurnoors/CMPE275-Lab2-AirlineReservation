CREATE TABLE passenger(
   id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(20) NOT NULL,
   lastname VARCHAR(20) NOT NULL,
   age INT,
   gender VARCHAR(10),
   phone VARCHAR(10),
   PRIMARY KEY (id),
   UNIQUE(phone)
);
CREATE TABLE flight(
   number VARCHAR(10) NOT NULL,
   price INT,
   fromSrc VARCHAR(20) NOT NULL,
   toDest VARCHAR(20) NOT NULL,
   departureTime DATE,
   arrivalTime DATE,
   seatsLeft INT,
   model VARCHAR(10),
   PRIMARY KEY (number)
);
CREATE TABLE reservation(
   orderNumber VARCHAR(10) NOT NULL,
   pId VARCHAR(10),
   flights VARCHAR(10),
   price INT,
   PRIMARY KEY (orderNumber)
);
CREATE TABLE reservation(
   capacity INT,
   model VARCHAR(10),
   manufacturer VARCHAR(10), 
   yearOfManufacture INT,
   PRIMARY KEY (model)
);
