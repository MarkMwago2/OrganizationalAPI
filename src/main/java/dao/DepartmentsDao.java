package dao;




import models.Departments;

import java.util.List;

public interface DepartmentsDao {
    //create
    void add (Departments department);
    // void addDepartmentToNews(Department department, News news)

    //read
    List<Departments> getAll();
    Departments findById(int id);
    // List<News> getAllNewsForADepartment(int departmentId);

    //update
    void update(int id, String name, String description, int numberOfEmployees);

    //delete
    void deleteById(int id);
    void clearAll();
}
