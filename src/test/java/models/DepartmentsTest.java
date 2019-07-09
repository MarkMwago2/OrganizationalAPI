package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
    }

    @Test
    public void setId() {

    }

    @Test
    public void equals1() {
    }

    @Test
    public void hashCode1() {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Departments testDepartment= setupDepartments();
        assertEquals("marketing", testDepartment.getName());
    }

    @Test
    public void setNameSetsCorrectName() throws Exception {
        Departments testDepartment= setupDepartments();
        testDepartment.setName("marketing");
        assertNotEquals("pr", testDepartment.getName());
    }

    @Test
    public void getDescriptionReturnsCorrectDescription() throws Exception{
        Departments testDepartment = setupDepartments();
        assertEquals("online promotion", testDepartment.getDescription());
    }

    @Test
    public void setDescriptionSetsCorrectDescription() throws Exception {
        Departments testDepartment= setupDepartments();
        testDepartment.setDescription("online promotion");
        assertNotEquals("awareness", testDepartment.getDescription());
    }

    @Test
    public void getNumberOfEmployeesReturnsCorrectNumberofEmployee() {
        Departments testDepartment = setupDepartments();
        assertEquals(6, testDepartment.getNumberOfEmployees());
    }

    @Test
    public void setNumberOfEmployeesSetsCorrectNumber() {
        Departments testDepartment= setupDepartments();
        testDepartment.setNumberOfEmployees(6);
        assertNotEquals(8, testDepartment.getNumberOfEmployees());
    }



    //helper
    public  Departments setupDepartments(){
        return new Departments("marketing","online promotion",6);
    }
}
