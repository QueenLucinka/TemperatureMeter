
-- drop database if exists Tempdata;
create database TempData;
use TempData;

CREATE TABLE temp_reading (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    temperature FLOAT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

drop user if exists 'user'@'localhost';
create user 'user'@'localhost' identified by 'password';
grant all privileges on TempData.* to 'user'@'localhost';
flush privileges;

-- another file
USE TempData;
SELECT * FROM temp_reading;

-- another file
DESCRIBE temp_reading;