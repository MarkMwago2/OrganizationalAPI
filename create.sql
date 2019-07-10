SET MODE PostgreSQL;


CREATE TABLE IF NOT EXISTS departments(
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 numberOfemployees INTEGER,
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
);

CREATE TABLE users (
 id SERIAL PRIMARY KEY,
 roles VARCHAR,
 post VARCHAR,
 departmentId INTEGER,
 createdat BIGINT
);


CREATE DATABASE OrgAPI_test WITH TEMPLATE OrgAPI;
