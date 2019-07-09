package dao;

import models.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import dao.Sql2oNewsDao;
import dao.Sql2oDepartmentsDao;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {
    private Connection conn;
    private Sql2oNewsDao newsDao;
    private Sql2oDepartmentsDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "mark", "87654321");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }



    @Test
    public void add() throws Exception{

        News testNews = setupNewNews();
        int originalNewsId = testNews.getId();
        newsDao.add(testNews);
        assertNotEquals(originalNewsId,testNews.getId());
    }

    @Test
    public void getAll() throws Exception{
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void addedNewsAreReturnedFromGetAll() throws Exception {
        News testNews = setupNewNews();
        newsDao.add(testNews);
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void deleteById() throws Exception{
        News news = setupNewNews();
        newsDao.add(news);
        newsDao.deleteById(news.getId());
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception{

        News testNews = setupNewNews();
        News otherNews = setupNewNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());

    }


    // helpers

    public News setupNewNews(){
        return new News("Achiements from online marketing this month", 1);
    }
}