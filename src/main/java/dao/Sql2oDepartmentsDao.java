package dao;

import models.Departments;
import models.Departments;
import models.Users;
import models.News;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {

    private final Sql2o sql2o;
    public  Sql2oDepartmentsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }
    @Override
    public void add(Departments department) {
        String sql = "INSERT INTO departments (department, description,  number_employees) VALUES (:department, :description, :number_employees)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Departments findById(int id) {
        String sql = "SELECT * FROM departments WHERE id=:id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public List<Departments> getAll() {
        String sql = "SELECT * FROM departments";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Departments.class);
        }
    }

    @Override
    public List<News> getAllNews(int departmentId) {
        String sql = "SELECT * FROM news WHERE departmentId=:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

    //many to many relationships
    @Override
    public void addDepartmentToEmployees(Departments department, Users employees) {
        String sql = "INSERT INTO departments_employees(departmentId, employeeId) VALUES (:departmentId, :employeeId)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("employeeId", employees.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Users> getAllEmployeesForADepartment(int departmentId) {
        ArrayList<Users> allUsers = new ArrayList<>();
        String matchToGetTheUserIds = "SELECT employeeId FROM departments_employees WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            List<Integer> allEmployeesIds = conn.createQuery(matchToGetTheUserIds)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Integer.class);
            for(Integer employeeId: allEmployeesIds){
                String getFromUsers = "SELECT * FROM employees WHERE id=:employeeId";
                allUsers.add(conn.createQuery(getFromUsers)
                        .addParameter("employeeId", employeeId)
                        .executeAndFetchFirst(Users.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allUsers;
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}