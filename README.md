QUANTIS-CHALLENGE
---------

A java REST API.

_______________

## Installation

Requires Java 11, MySQL and Maven.

Automated database creation is not currently working.
For this reason, run the following before running the app:

    CREATE DATABASE quantis_db;
    CREATE USER 'app_usr'@'%' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON quantis_db . * TO 'app_usr'@'%';

Clone this Git repository, then move to the directory and run the following command:

To run the app:

    mvn spring-boot:run

_______________


## License

This repository uses the [MIT License](/LICENSE).
