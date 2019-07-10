package dao;

import models.DB;
import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao{

    private final Sql2o sql2o;
    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Departments department) {
        String sql = "INSERT INTO department (name, description, numberOfEmployees) VALUES (:name, :description, :numberOfEmployees)";
        try (Connection con = DB.sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Departments> getAll() {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM department")
                    .executeAndFetch(Departments.class);
        }
    }

    @Override
    public Departments findById(int id) {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM department WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public void update(int newId, String newName, String newDescription, int newNumberOfEmployees) {
        String sql = "UPDATE department SET (name, description, numberOfEmployees) = (:name, :description, :numberOfEmployees) WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", newId)
                    .addParameter("name", newName)
                    .addParameter("description", newDescription)
                    .addParameter("numberOfEmployees", newNumberOfEmployees)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from department WHERE id = :id";
        String deleteJoin = "DELETE from department_news WHERE departmentId = :departmentId";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("departmentId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from department";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentToNews(Departments department, News news){
        String sql = "INSERT INTO department_news (departmentId, newsId) VALUES (:departmentId, :newsId)";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("newsId", news.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAllNewsByDepartment(int departmentId){
        List<News> news = new ArrayList(); //empty list
        String joinQuery = "SELECT newsId FROM department_news WHERE departmentId = :departmentId";

        try (Connection con = DB.sql2o.open()) {
            List<Integer> allNewsIds = con.createQuery(joinQuery)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);
            for (Integer foodId : allNewsIds){
                String newsQuery = "SELECT * FROM news WHERE id = :newsId";
                news.add(
                        con.createQuery(newsQuery)
                                .addParameter("newsId", foodId)
                                .executeAndFetchFirst(News.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

        return news;
    }


}


