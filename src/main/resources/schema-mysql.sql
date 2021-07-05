CREATE DATABASE quantis_db;

CREATE USER 'app_usr'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON quantis_db . * TO 'app_usr'@'%';