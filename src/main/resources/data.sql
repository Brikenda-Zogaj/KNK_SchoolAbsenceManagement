CREATE DATABASE projekti;
USE projekti;
CREATE TABLE users(
ID INT primary key auto_increment,
USERNAME VARCHAR(20) NOT NULL,
PASSWORD VARCHAR(1024) NOT NULL,
salt VARCHAR(500)
);
INSERT INTO users (USERNAME,PASSWORD) VALUE("admin","admin");

CREATE TABLE signup_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(1024) NOT NULL,
    subject VARCHAR(50) NOT NULL,
    salt VARCHAR(500)
);



