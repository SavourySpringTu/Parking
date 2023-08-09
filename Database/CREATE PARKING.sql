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
    type BOOLEAN,
    timestart TIME,
    timeend TIME,
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
    time DATETIME,
    day INT,
    number_phone CHAR(15),
    number_vehicle CHAR(10),
    type BOOLEAN,
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
INSERT INTO role(id,name) VALUES ('R02','Cashier');

INSERT INTO user(username,name,password,id_role,status) VALUES ('tubui','Tu','123','R01',0);
INSERT INTO user(username,name,password,id_role,status) VALUES ('giabao','Bao','123','R01',0);
INSERT INTO user(username,name,password,id_role,status) VALUES ('khaibui','Khai','123','R02',0);
INSERT INTO user(username,name,password,id_role,status) VALUES ('thanhnhan','Nhan','123','R02',0);

INSERT INTO positions(type,camera,status) VALUES (0,'abc',0);
INSERT INTO positions(type,camera,status) VALUES (1,'abc',0);
INSERT INTO positions(type,camera,status) VALUES (1,'abc',0);
INSERT INTO positions(type,camera,status) VALUES (0,'abc',0);

INSERT INTO type(id,type,price) VALUES ('T01',0,4000);
INSERT INTO type(id,type,price) VALUES ('T02',1,6000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T03',0,'00:00:00','06:59:59',15000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T04',0,'07:00:00','16:59:59',5000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T05',0,'17:00:00','23:59:59',10000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T06',1,'00:00:00','06:59:59',17000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T07',1,'07:00:00','16:59:59',7000);
INSERT INTO type(id,type,timestart,timeend,price) VALUES ('T08',1,'17:00:00','23:59:59',12000);


INSERT INTO customer(name,number_phone,number_vehicle,type,time,day,status) VALUES ('Bao','036201001464','59-h13784',0,'2023-07-28 00:00:00',15,0);
INSERT INTO customer(name,number_phone,number_vehicle,type,time,day,status) VALUES ('Phuong','0907785775','30f22222',1,'2023-07-28 00:00:00',30,0);
