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
  
  /*
   * This method creates a new database and adds
   * all relevant empty tables to have data inserted
   * at a later time.
   */
  
  public void createNewDatabase() {
    
    // specify the path and name of the new database
    String url = "jdbc:sqlite:linux_metrics.db";
    
    // create the database
    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    
    /*
     * All of the SQLite Create Table statements
     */
    String createVolatile = "CREATE TABLE IF NOT EXISTS volatile_mem_stats (\n"
        + "timestamp integer PRIMARY KEY, \n"
        + "mem_total integer NOT NULL, \n"
        + "mem_available integer NOT NULL, \n"
        + "swap_size integer NOT NULL, \n"
        + "swap_available integer NOT NULL\n"
        + ");";
    
    String createPersistent = "CREATE TABLE IF NOT EXISTS persistent_storage (\n"
        + "disk_name text PRIMARY KEY, \n"
        + "total_size integer NOT NULL\n"
        + ");";
    
    String createPersistentStats = "CREATE TABLE IF NOT EXISTS persistent_storage_stats (\n"
        + "disk_name text PRIMARY KEY, \n"
        + "used integer NOT NULL, \n"
        + "available integer NOT NULL, \n"
        + "used_percent integer NOR NULL, \n"
        + "FOREIGN KEY(disk_name) REFERENCES persistent_storage(disk_name)\n"
        + ");";
    
    String createNetworking = "CREATE TABLE IF NOT EXISTS networking_stats (\n"
        + "pid integer NOT NULL, \n"
        + "local_ip text NOT NULL, \n"
        + "foreign_ip text NOT NULL, \n"
        + "program_name text NOT NULL, \n"
        + "timestamp date_time text NOT NULL \n"
        + ");";
    
    String createCPUInfo = "CREATE TABLE IF NOT EXISTS cpu_info (\n"
        + "cpu_number integer PRIMARY KEY, \n"
        + "max_clock_rate float NOT NULL, \n"
        + "max_temp NOT NULL \n"
        + ");";
    
    String createCPUInterrupts = "CREATE TABLE IF NOT EXISTS cpu_interrupts (\n"
        + "cpu_number integer PRIMARY KEY, \n"
        + "interrupt_type text NOT NULL, \n"
        + "interrupt_count text NOT NULL, \n"
        + "timestamp date_time NOT NULL, \n"
        + "FOREIGN KEY(cpu_number) REFERENCES cpu_info(cpu_number)\n"
        + ");";
    
    String createCPUTimePerformance = "CREATE TABLE IF NOT EXISTS cpu_time_performance (\n"
        + "cpu_number integer PRIMARY KEY, \n"
        + "cpu_temp float NOT NULL, \n"
        + "clock_speed float NOT NULL, \n"
        + "timestamp date_time NOT NULL, \n"
        + "FOREIGN KEY(cpu_number) REFERENCES cpu_info(cpu_number)\n"
        + ");";
    
    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
      // create the tables
      stmt.execute(createVolatile);
      stmt.execute(createPersistent);
      stmt.execute(createPersistentStats);
      stmt.execute(createNetworking);
      stmt.execute(createCPUInfo);
      stmt.execute(createCPUInterrupts);
      stmt.execute(createCPUTimePerformance);
      
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

//TODO: Add batch update support with PreparedStatement objects
//TODO: Abstract execution code and make a static method in the utilities class
public void addData(){
String url = "jdbc:sqlite:linuxmetrics.db";
String sql = "insert into volatile_mem_stats values (1, 63, 2400, 40, 100);";
try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()){

      stmt.execute(sql);
      System.out.println("Table created successfully!");
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }  
}

