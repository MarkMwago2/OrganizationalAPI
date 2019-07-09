package dao;

import models.Users;

import java.util.List;

public interface UsersDao {
    //create
    void add(Users users);

    //read
    List<Users> getAll();
    List<Users> getAllUsersByDepartment(int departmentId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}