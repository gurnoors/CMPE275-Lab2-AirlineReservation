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
CREATE TABLE reservation(
   orderno INT NOT NULL AUTO_INCREMENT, 
   pid VARCHAR(10),
   flights VARCHAR(20),
   price INT,
   PRIMARY KEY (orderno)
);
CREATE TABLE flight (
  number varchar(255) NOT NULL,
  arrival_time datetime,
  departure_time datetime,
  description varchar(255),
  from_src varchar(255),
  capacity int(11) NOT NULL,
  manufacturer varchar(255),
  model varchar(255),
  year_of_manufacture int(11) NOT NULL,
  price int(11),
  seats_left int(11),
  to_dest varchar(255),
  PRIMARY KEY (number)
);
