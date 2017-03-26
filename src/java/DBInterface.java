/**
 * Class: DBInterface
 * @author Zach Stecher
 * 
 * This class holds all the methods to interact with
 * the program's SQLite database.
 */
import java.sql.*;

public class DBInterface {
  DBInterface(){
    System.out.println("DBInterface");
    
  }
  private Connection connect() {
    //SQLite connection string
    String url= "jdbc:sqlite:tests.db";
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url);
    }
    catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }
  
  /*
   * This method connects to an existing database if
   * it exists, and creates a new empty table with the
   * values passed to it.
   * 
   * (Currently has hardcoded values for testing purposes).
   */
  public static void createNewTable(){
    //SQLite connection string
    String url = "jdbc:sqlite:tests.db";
    
    String sql = "CREATE TABLE IF NOT EXISTS volatile (\n"
        + "mem_module_num integer PRIMARY KEY NOT NULL,\n"
        + "total_memory float,\n"
        + "speed float\n"
        + ")";
    
    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){
      
      stmt.execute(sql);
      System.out.println("Table created successfully!");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

//TODO: Add batch update support with PreparedStatement objects
//TODO: Abstract execution code and make a static method in the utilities class
public void addData(){
String url = "jdbc:sqlite:tests.db";
String sql = "insert into volatile values (1, 63.1, 2400);";
try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){

      stmt.execute(sql);
      System.out.println("Table created successfully!");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }

}  

  public void selectAll(){
    String sql = "SELECT mem_module_num, total_memory, speed FROM volatile";
    
    try(Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

      // loop through the result set
      while(rs.next()) {
        System.out.println("Module Number : " + rs.getInt("mem_module_num") + "\n" + 
                           "Total Memory  :" +rs.getFloat("total_memory")+ " GB" + "\n" +
                           "Memory Clock  : "+rs.getFloat("speed") + " mhz");
      }
    }
    catch(SQLException e){
      System.out.println(e.getMessage());
    }
  }
}

