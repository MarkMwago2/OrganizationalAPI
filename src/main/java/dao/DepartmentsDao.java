package dao;




import models.Departments;
import models.News;
import models.Users;

import java.util.List;

public interface DepartmentsDao {
    void add(Departments department);

    //Find by id
    Departments findById(int id);

    //Get all departments
    List<Departments> getAll();

    //Get all news for a department
    List<News> getAllNews(int departmentId);

    //many to many relationship
    //Add a department to a user
    void addDepartmentToEmployees(Departments department, Users employees);
    //many to many relationship
    //Get all users in a particular department
    List<Users> getAllEmployeesForADepartment(int employee_id);

    //delete
    //deleteById(int id);
    void clearAll();
}
