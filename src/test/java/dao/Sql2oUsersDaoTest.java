package dao;

import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {
    private Connection conn;
    private Sql2oUsersDao usersDao;
    private Sql2oDepartmentsDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "Bus-242-001/2014");
        usersDao = new Sql2oUsersDao(sql2o);
        departmentDao = new Sql2oDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() {
    }

    @Test
    public void getAll() throws Exception{
        Users users1 = setupUsers();
        Users users2 = setupUsers();
        assertEquals("mark", "mark");
    }

    @Test
    public void getAllUsersByDepartment() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void clearAll() {
    }

    //helper

    public Users setupUsers() {
        Users users = new Users("Kings", "Chief accountant", "management", 2);
        usersDao.add(users);
        return users;
    }

    public Users setupUsersForDepartment(Departments department) {
        Users users = new Users("Kings", "Chief accountant", "management", department.getId());
        usersDao.add(users);
        return users;
    }

    public Departments setupDepartment() {
        Departments department = new Departments("Finance", "Money man", 2);
        departmentDao.add(department);
        return department;
    }
}
