package dao;



import models.Departments;
import models.News;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentsDaoTest {

    private static Connection conn;
    private static Sql2oDepartmentsDao departmentDao;
    private static Sql2oNewsDao newsDao;
    private static Sql2oUsersDao usersDao;


    @Rule
    public DatabaseRule database = new DatabaseRule();


    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/orgapi_test";
        Sql2o sql2o = new Sql2o(connectionString, "mark", "87654321");
        departmentDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
//        conn = sql2o.open();
    }

//    @After
//    public void tearDown() throws Exception {
//        System.out.println("clearing database");
//        departmentDao.clearAll();
//        usersDao.clearAll();
//        newsDao.clearAll();
//    }
//    @AfterClass     //changed to @AfterClass (run once after all tests in this file completed)
//    public static void shutDown() throws Exception{ //changed to static
////        conn.close(); // close connection once after this entire test file is finished
//        System.out.println("connection closed");
//    }

    @Test
    public void add() throws Exception{
        Departments testDepartment = setupDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());

    }
//
//
    @Test
    public void addedDepartmentAreReturnedFromGetAll() throws Exception {
        Departments testDepartment = setupDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void noDepartmentReturnsEmptyList() throws Exception{
        assertEquals(0, departmentDao.getAll().size());
    }
//
    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Departments testDepartment = setupDepartment();
        Departments otherDepartment = setupDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }
//
//    @Test
//    public void updateCorrectlyUpdatesAllFields() throws Exception {
//
//        Departments testDepartment = setupDepartment();
//        departmentDao.add(testDepartment);
//        departmentDao.update(testDepartment.getId(), "Accounts", "handles accounting business", 32);
//        Departments foundDepartment = departmentDao.findById(testDepartment.getId());
//        assertEquals("Accounts", foundDepartment.getName());
//        assertEquals("handles accounting business", foundDepartment.getDescription());
//        assertEquals(32, foundDepartment.getNumberOfEmployees());
//    }
//
//
    @Test
    public void deleteByIdDeletesCorrectDepartments() throws Exception {
        Departments testDepartment = setupDepartment();
//        Department otherDepartment = setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(1, departmentDao.getAll().size());
    }
//
    @Test
    public void clearAll() throws Exception {
        Departments testDepartment = setupDepartment();
        Departments otherDepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }
//
//
//
//
//    @Test
//    public void getAllNewsForADepartmentReturnsNewsCorrectly() throws Exception {
//        News testNews  = new News("Impressive growth", 1);
//        newsDao.add(testNews);
//
//        News otherNews  = new News("No 1 products", 1);
//        newsDao.add(otherNews);
//
//        Departments testDepartment = setupDepartment();
//        departmentDao.add(testDepartment);
//        departmentDao.addDepartmentToNews(testDepartment,testNews);
//        departmentDao.addDepartmentToNews(testDepartment,otherNews);
//
//        News[] news = {testNews, otherNews}; //oh hi what is this?
//
//        assertEquals(Arrays.asList(news), departmentDao.getAllNewsByDepartment(testDepartment.getId()));
//    }
//
////    @Test
////    public void deletingDepartmentAlsoUpdatesJoinTable() throws Exception {
////        News testNews  = new News("Seafood");
////        newsDao.add(testNews);
////
////        Department testDepartment = setupDepartment();
////        departmentDao.add(testDepartment);
////
////        Department altDepartment = setupAltDepartment();
////        departmentDao.add(altDepartment);
////
////        departmentDao.addDepartmentToNews(testDepartment,testNews);
////        departmentDao.addDepartmentToNews(altDepartment, testNews);
////
////        DepartmentDao.deleteById(testDepartment.getId());
////        assertEquals(0, DepartmentDao.getAllNewsByDepartment(testDepartment.getId()).size());
////    }
//
//
//    //helpers
//
    public Departments setupDepartment (){
        Departments department = new Departments("Marketing","marketing",24);
        departmentDao.add(department);
        return department;
    }

    public Departments setupAltDepartment (){
        Departments department = new Departments("Marketing", "adverts", 12);
        departmentDao.add(department);
        return department;
    }
}