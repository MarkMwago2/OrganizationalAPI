package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);
    //void addNewsToDepartments(News news, Department department);

    //read
    List<News> getAll();
    // List<Departments> getAllDepartmentsForANews(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}


