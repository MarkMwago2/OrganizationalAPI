package dao;

import models.DB;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUsersDao implements UsersDao {
    private final Sql2o sql2o;
    public Sql2oUsersDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Users users) {
        String sql = "INSERT INTO users (name,duties,position,departmentId) VALUES (:name,:duties,:position,:departmentId);";

        try (Connection con = DB.sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(users)
                    .executeUpdate()
                    .getKey();
            users.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public List<Users> getAll() {
        String sql = "SELECT * FROM users";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public List<Users> getAllUsersByDepartment(int departmentId) {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public List<Users> getAllUsersByDepartmentSortedNewestToOldest(int departmentId) {
        List<Users> unsortedUsers = getAllUsersByDepartment(departmentId);
        List<Users> sortedUsers = new ArrayList<>();
        int i = 1;
//        for (Users users : unsortedUsers){
//            int comparisonResult;
//            if (i == unsortedUsers.size()) {
//                if (users.compareTo(unsortedUsers.get(i-1)) == -1){
//                    sortedUsers.add(0, unsortedUsers.get(i-1));
//                }
//                break;
//            }
//
//            else {
//                if (users.compareTo(unsortedUsers.get(i)) == -1) {
//                    sortedUsers.add(0, unsortedUsers.get(i));
//                    i++;
//                } else if (users.compareTo(unsortedUsers.get(i)) == 0) {
//                    sortedUsers.add(0, unsortedUsers.get(i));
//                    i++;
//                } else {
//                    sortedUsers.add(0, unsortedUsers.get(i));
//                    i++;
//                }
//            }
//        }
        return sortedUsers;
    }



    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}