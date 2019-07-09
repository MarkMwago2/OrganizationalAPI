package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNamereturnsName() throws Exception {
        Users testUsers= setupusers();
        assertEquals("Mark", testUsers.getName());
    }

    @Test
    public void setName() {
        Users testUsers= setupusers();
        testUsers.setName("Mark");
        assertNotEquals("Mwago", testUsers.getName());
    }

    @Test
    public void getRoleReturnsRole() {
        Users testUsers= setupusers();
        assertEquals("online marketing", testUsers.getRole());
    }

    @Test
    public void setRole() {
        Users testUsers= setupusers();
        testUsers.setRole("online marketing");
        assertNotEquals("field work", testUsers.getRole());
    }

    @Test
    public void getPositionReturnsPosition() {
        Users testUsers= setupusers();
        assertEquals("marketing executive", testUsers.getPosition());
    }

    @Test
    public void setPosition() {
        Users testUsers= setupusers();
        testUsers.setPosition("marketing executive");
        assertNotEquals("sales rep", testUsers.getPosition());
    }

    @Test
    public void getIdReturnsId() {

    }

    @Test
    public void setId() {
    }



    //helper
    public  Users setupusers(){
        return new Users("Mark","online marketing","marketing executive",1);
    }
}