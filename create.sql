CREATE DATABASE OrgAPI;
\c OrgAPI;
CREATE TABLE departments(department VARCHAR, description VARCHAR, employeeId int);
CREATE TABLE users(name VARCHAR, post VARCHAR, role VARCHAR, address VARCHAR);
CREATE TABLE news(title VARCHAR, content VARCHAR, department VARCHAR);
CREATE TABLE departments_users(departmentId int, employeeId int);
CREATE DATABASE OrgAPI_test WITH TEMPLATE OrgAPI;