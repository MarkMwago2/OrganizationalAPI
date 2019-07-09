SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 numberOfEmployees VARCHAR,

);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
 departmentId INTEGER
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 roles VARCHAR,
 position VARCHAR,
 departmentId INTEGER
);