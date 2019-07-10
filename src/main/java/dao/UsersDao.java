package dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface UsersDao {
    void add(Users employees);

    //Find a user by the id
    Users findById(int id);

    //Get all users
    List<Users> getAllUsers();

    //Add a user to a department
    void addEmployeeToDepartment(Users employees, Departments department);

    //Get all departments Employee belongs to
    List<Departments> getAllDepartmentsForEmployee(int employee_id);
    void clearAll();
}