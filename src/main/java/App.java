import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oUsersDao;
import dao.Sql2oNewsDao;
import Exceptions.ApiException;
import dao.UsersDao;
import models.Departments;
import models.Users;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import static spark.Spark.port;

public class App {
    public void main(String[]args ){
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        Integer port;
//        if (processBuilder.environment().get("PORT")!= null){
//            port = Integer.parseInt(processBuilder.environment().get("PORT"));
//        }else {
//            port = 4567;
//        }
//        port(port);
//
        Sql2oDepartmentsDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oUsersDao UsersDao;
        Connection conn;
        Gson gson = new Gson();

        String connectingString = "jdbc:postgresql://localhost:5432/newsapi";
        Sql2o sql2o = new Sql2o(connectingString,"","");
        departmentDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        UsersDao = new Sql2oUsersDao(sql2o);

        //post: Add news
        post("/news/new","application/json",(request, response) -> {
            News news = gson.fromJson(request.body(),News.class);
            newsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        //get:View All news
        get("/news","application/json",(request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            return gson.toJson(newsDao.getAllNews(departmentId));
        });

        //post:Add department
        post("/department/new","application/json",(request, response) -> {
            Departments department = gson.fromJson(request.body(),Departments.class);
            departmentDao.add(department);
            response.status(201);
            return gson.toJson(department);
        });

        //get:view all departments
        get("/department","application/json",(request, response) -> {
            return gson.toJson(departmentDao.getAll());
        });

        //post: Add news to department
        post("/department/:id/news/new","application/json",(request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            News news = gson.fromJson(request.body(),News.class);
            news.setId(id);
            newsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        //get:View all news for department
        get("/departments/:id/depNews", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            return gson.toJson(newsDao.getAllNews(id));
        });

        //post:add a user(employees)
        post("/employees/new", "application/json", (request, response) -> {
            Users employees = gson.fromJson(request.body(), Users.class);
            UsersDao.add(employees);
            response.status(201);
            return gson.toJson(employees);
        });

        //Get: View all users
        get("/users", "application/json", (request, response) -> {
            return gson.toJson(UsersDao.getAllUsers());
        });

        //post:Add a department to a user
        post("employees/employeeId/department/:departmentId","application/json",(request, response) -> {
            int emplyeeId = Integer.parseInt(request.params("employeeId"));
            int departmentId = Integer.parseInt(request.params("departmentId"));
            Users employeesFound = UsersDao.findById(emplyeeId);
            Departments departmentfound = departmentDao.findById(departmentId);

            if (departmentfound != null && employeesFound != null){
                departmentDao.addDepartmentToEmployees(departmentfound,employeesFound);
                response.status(201);
                return gson.toJson("Employees and Department have been associated");
            }
            else {
                throw new ApiException(404, String.format("Employee or Department does not exist"));
            }
        });

        //get:View all departments a user belongs to

        get("/employees/:employeeId/department","application/json",(request, response) -> {
            int employeeId = Integer.parseInt(request.params("employeeId"));
            Users employees = UsersDao.findById(employeeId);

            if (employees == null){
                throw new Exception("No Employee with that id");
            }else if(UsersDao.getAllDepartmentsForEmployee(employeeId).size() == 0){
                return "{\"message\":\"Employee not associated with any department\"}";
            }else {
                return gson.toJson(UsersDao.getAllDepartmentsForEmployee(employeeId));
            }
        });

        //filter for response
        after((request, response) -> {
            response.type("application/json");
        });


    }
}