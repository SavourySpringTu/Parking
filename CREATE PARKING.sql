
DROP DATABASE parking;
CREATE DATABASE parking;
USE parking;
CREATE TABLE role(
	id CHAR(10) PRIMARY KEY NOT NULL,
    name CHAR(20)
);
CREATE TABLE user(
	username CHAR(20) PRIMARY KEY NOT NULL,
    name CHAR(20),
    password CHAR(20),
    id_role CHAR(10),
    status BOOLEAN,
	FOREIGN KEY (id_role) REFERENCES role(id)
);
CREATE TABLE type(
	id CHAR(10) PRIMARY KEY NOT NULL,
    timestart TIME,
    price INT
);
CREATE TABLE positions(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type BOOLEAN,
    camera CHAR(100),
    status BOOLEAN
);
CREATE TABLE warehouse(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    timein DATETIME,
    timeout DATETIME,
    id_ticket INT,
    status BOOLEAN
);
CREATE TABLE customer(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name CHAR(10),
    number_phone CHAR(15),
    number_vehicle CHAR(10),
    status BOOLEAN
);
CREATE TABLE ticket(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    timein DATETIME,
    timeout DATETIME,
    number_vehicle CHAR(10),
    type_vehicle BOOLEAN,
    id_type CHAR(10),
    id_position INT,
    id_user CHAR(20),
    id_warehouse INT,
    id_customer INT,
    FOREIGN KEY (id_type) REFERENCES type(id),
    FOREIGN KEY (id_position) REFERENCES positions(id),
	FOREIGN KEY (id_user) REFERENCES user(username) ,
    FOREIGN KEY (id_warehouse) REFERENCES warehouse(id),
    FOREIGN KEY (id_customer) REFERENCES customer(id)
);
CREATE TABLE image(
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    url CHAR(100),
    id_ticket INT,
    type BOOLEAN,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id)
);

INSERT INTO role(id,name) VALUES ('R01','Admin');