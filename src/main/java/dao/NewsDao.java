package dao;

import models.Departments;
import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);
    void addNewsToDepartment(News news, Departments department);

    //read
    List<News> getAll();
    List<Departments> getAllDepartmentsForANews(int id);
    News findById(int id);
    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}