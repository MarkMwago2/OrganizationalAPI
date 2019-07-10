package dao;

;
import models.Departments;
import models.Users;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUsersDao implements UsersDao {

    private final Sql2o sql2o;

    public Sql2oUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users employees) {
        String sql = "INSERT INTO users(name, position, role,address) VALUES (:name, :position, :role,:address)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(employees)
                    .executeUpdate()
                    .getKey();
            employees.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Users findById(int id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Users.class);
        }
    }

    @Override
    public List<Users> getAllEmployees() {
        String sql = "SELECT * FROM users";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Users.class);
        }
    }

    @Override
    public void addEmployeeToDepartment(Users employees, Departments department) {
        String sql = "INSERT INTO departments_employees(departmentId, employeeId) VALUES (:departmentId, :employeeId)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("employeeId", employees.getId())
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Departments> getAllDepartmentsForEmployee(int employeeId) {
        ArrayList<Departments> allDepartments = new ArrayList<>();
        String matchToGetDepartmentId = "SELECT departmentId FROM departments_employees WHERE employeeId =:employeeId";
        try(Connection conn = sql2o.open()){
            List<Integer> allDepartmentIds = conn.createQuery(matchToGetDepartmentId).addParameter("employeeId", employeeId)
                    .executeAndFetch(Integer.class);
            for(Integer departmentId : allDepartmentIds){
                String getFromDepartments = "SELECT * FROM departments WHERE id=:departmentId";
                allDepartments.add(conn.createQuery(getFromDepartments).addParameter("departmentId", departmentId).executeAndFetchFirst(Departments.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allDepartments;
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
