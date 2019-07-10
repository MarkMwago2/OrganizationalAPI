import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import models.*;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import Exceptions.ApiException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());

        Sql2oUsersDao userDao;
        Sql2oDepartmentsDao departmentDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();


        Sql2o sql2o = DB.sql2o;
        departmentDao = new Sql2oDepartmentsDao(sql2o);
        userDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        //CREATE

        post("/departments/new", "application/json", (req, res) -> {
            Departments department = gson.fromJson(req.body(), Departments.class);
            departmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        post("/departments/:departmentId/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = gson.fromJson(req.body(), News.class);
            news.setDepartmentId(departmentId);
            newsDao.add(news);
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        post("/departments/:departmentId/users/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            Users users = gson.fromJson(req.body(), Users.class);
            users.setDepartmentId(departmentId);
            userDao.add(users);
            res.status(201);
            res.type("application/json");
            return gson.toJson(users);
        });

        post("/users/new", "application/json", (req, res) -> {
            Users user = gson.fromJson(req.body(), Users.class);
            userDao.add(user);
            res.status(201);
            res.type("application/json");
            return gson.toJson(user);
        });

        post("/news/new", "application/json", (req, res) -> {
            res.type("application/json");
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        //Read


        get("/departments", "application/json", (req, res) -> {
            res.type("application/json");
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> {

            int departmentId = Integer.parseInt(req.params("id"));
            Departments departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            res.type("application/json");
            return gson.toJson(departmentToFind);
        });

//        get("/departments/:id/news", "application/json", (req, res) -> {
//            int departmentId = Integer.parseInt(req.params("id"));
//
//            Departments departmentToFind = departmentDao.findById(departmentId);
//            List<News> allNews;
//
//            if (departmentToFind == null){
//                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
//            }
//
////            allNews = newsDao.getAllNewsByDepartments(departmentId);
//            res.type("application/json");
//            return gson.toJson(allNews);
//        });
        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Departments departmentToFind = departmentDao.findById(departmentId);
            List<Users> allUsers;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allUsers = userDao. getAllUsersByDepartment(departmentId);
            res.type("application/json");
            return gson.toJson(allUsers);
        });
        get("/news", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(newsDao.getAll());
        });

        get("/users", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(userDao.getAll());

        });


//        //FILTERS
//        exception(ApiException.class, (exception, req, res) -> {
//            ApiException err = exception;
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("status", err.getStatusCode());
//            jsonMap.put("errorMessage", err.getMessage());
//            res.type("application/json");
//            res.status(err.getStatusCode());
//            res.body(gson.toJson(jsonMap));
//        });


//        after((req, res) ->{
//            res.type("application/json");
//        });

        //templates

        get("/departments/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "department-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String departmentName = request.queryParams("departmentName");
            String description = request.queryParams("description");
            int numberOfEmployees = Integer.parseInt(request.queryParams("numberOfEmployees"));
            Departments newDepartment = new Departments(departmentName, description, numberOfEmployees);
            departmentDao.add(newDepartment);
            model.put("departments", departmentDao.getAll());
            return new ModelAndView(model, "department.hbs");
        }, new HandlebarsTemplateEngine());
        get("/departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "department.hbs");
        },new HandlebarsTemplateEngine());
        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "user.hbs");
        },new HandlebarsTemplateEngine());
        post("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String userName = request.queryParams("userName");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            String role = request.queryParams("role");
            Users newUser = new Users("name", "role", "position", departmentId);
            userDao.add(newUser);
            model.put("users", userDao.getAll());
            return new ModelAndView(model, "user.hbs");
        }, new HandlebarsTemplateEngine());
        get("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "news-form.hbs");
        },new HandlebarsTemplateEngine());
        post("/news", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String content = request.queryParams("content");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            News newNews = new News(content, departmentId);
            newsDao.add(newNews);
            model.put("news", newsDao.getAll());
            return new ModelAndView(model, "news.hbs");
        }, new HandlebarsTemplateEngine());
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        },new HandlebarsTemplateEngine());

    }
}