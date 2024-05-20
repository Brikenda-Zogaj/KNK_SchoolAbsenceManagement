CREATE DATABAse projekti;
USE projekti;
CREATE TABLE users(
ID INT primary key auto_increment,
USERNAME VARCHAR(20) NOT NULL,
PASSWORD VARCHAR(20) NOT NULL
);
INSERT INTO users (USERNAME,PASSWORD) VALUE("admin","admin");


