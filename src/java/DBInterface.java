import java.sql.*;

public class DBInterface {
  DBInterface(){
    System.out.println("DBInterface");
    
  }
  
  public static void createNewTable(){
    String url = "jdbc:sqlite:tests.db";
    
    String sql = "CREATE TABLE IF NOT EXISTS volatile (\n"
        + "mem_module_num integer PRIMARY KEY NOT NULL,\n"
        + "total_memory float,\n"
        + "speed float\n"
        + ")";
    
    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){
      
      stmt.execute(sql);
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}

