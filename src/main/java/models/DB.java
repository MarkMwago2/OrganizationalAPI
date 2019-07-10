package models;
import org.sql2o.Sql2o;

public class DB {
    //    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/orgapi", "mark", "87654321");
//}
public static     String connectionString = "jdbc:postgresql://ec2-54-243-193-59.compute-1.amazonaws.com:5432/d1nmilqr2u45o7";
  public  static Sql2o sql2o = new  Sql2o(connectionString, "xoxogfswxejwhi", "2786d04ca75ad052e17cbf56780cf7fd0469923d8fd243c377840cc52d6a8baa");
}