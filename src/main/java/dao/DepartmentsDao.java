package dao;


import models.Departments;
import models.News;

import java.util.List;

public interface DepartmentsDao {
    //create
    void add (Departments department);
    void addDepartmentToNews(Departments department, News news);

    //read
    List<Departments> getAll();
    Departments findById(int id);
    List<News> getAllNewsByDepartment(int departmentId);

    //update
    void update(int id, String name, String description, int numberOfEmployees);

    //delete
    void deleteById(int id);
    void clearAll();
}