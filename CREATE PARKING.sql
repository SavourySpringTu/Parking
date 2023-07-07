DROP DATABASE parking;
CREATE DATABASE parking;
USE parking;
CREATE TABLE role(
	id CHAR(10) PRIMARY KEY NOT NULL,
    name CHAR(20)
);
CREATE TABLE user(
	id CHAR(10) PRIMARY KEY NOT NULL,
    name CHAR(20),
    password CHAR(20),
    id_role CHAR(10),
	FOREIGN KEY (id_role) REFERENCES role(id)
);
CREATE TABLE revenue(
	id DATE PRIMARY KEY NOT NULL,
    total int
);
CREATE TABLE positions(
	id INT PRIMARY KEY NOT NULL,
    type BOOLEAN,
    camera CHAR(30),
    status BOOLEAN
);
CREATE TABLE ticket(
	id INT PRIMARY KEY NOT NULL,
    timein TIME,
    timeout TIME,
    number CHAR(10),
    price INT,
    type_ticket BOOLEAN,
    type_vehicle BOOLEAN,
    id_revenue DATE,
    id_positions INT,
    id_user CHAR(10),
    FOREIGN KEY (id_revenue) REFERENCES revenue(id),
    FOREIGN KEY (id_positions) REFERENCES positions(id),
	FOREIGN KEY (id_user) REFERENCES user(id) 
);
CREATE TABLE image(
	id int PRIMARY KEY NOT NULL,
    url CHAR(100),
    id_ticket INT,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id)
);
CREATE TABLE warehouse(
	id INT PRIMARY KEY NOT NULL,
    timein TIME,
    timeout TIME,
    id_ticket INT,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id)
);

INSERT INTO role(id,name) VALUES ('R01','Admin');