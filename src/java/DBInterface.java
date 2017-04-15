/**
 * Class: DBInterface
 * @author Zach Stecher
 * 
 * This class holds all the methods to interact with
 * the program's SQLite database.
 */
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;    
import java.util.Date;

public class DBInterface {
 private String db;

  public DBInterface(){
    this.db= "jdbc:sqlite:linux_metrics.db";
    System.out.println("DBInterface");
    
  }
  
  /*
   * This method creates a new database and adds
   * all relevant empty tables to have data inserted
   * at a later time.
   */
  
  public void createNewDatabase() {
    
    // specify the path and name of the new database
    
    // create the database
    try (Connection conn = DriverManager.getConnection(this.db)) {
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
        + "mem_total integer NOT NULL, \n"
        + "mem_available integer NOT NULL, \n"
        + "swap_size integer NOT NULL, \n"
        + "swap_available integer NOT NULL, \n"
        + "timestamp text NOT NULL\n"
        + ");";
    
    String createPersistent = "CREATE TABLE IF NOT EXISTS persistent_storage (\n"
        + "disk_name text PRIMARY KEY, \n"
        + "total_size integer NOT NULL\n"
        + ");";
    
    String createPersistentStats = "CREATE TABLE IF NOT EXISTS persistent_storage_stats (\n"
        + "disk_name text, \n"
        + "used integer NOT NULL, \n"
        + "available integer NOT NULL, \n"
        + "used_percent integer NOR NULL, \n"
        + "timestamp text \n"
        + ");";
    
    String createNetworking = "CREATE TABLE IF NOT EXISTS networking_stats (\n"
        + "pid integer NOT NULL, \n"
        + "local_ip text NOT NULL, \n"
        + "foreign_ip text NOT NULL, \n"
        + "program_name text NOT NULL, \n"
        + "timestamp text NOT NULL \n"
        + ");";
    
    String createCPUInfo = "CREATE TABLE IF NOT EXISTS cpu_info (\n"
        + "cpu_number integer PRIMARY KEY, \n"
        + "max_clock_rate float NOT NULL, \n"
        + "max_temp float NOT NULL \n"
        + ");";
    
    String createCPUInterrupts = "CREATE TABLE IF NOT EXISTS cpu_interrupts (\n"
        + "cpu_number integer NOT NULL, \n"
        + "interrupt_type text NOT NULL, \n"
        + "interrupt_count text NOT NULL, \n"
        + "timestamp text NOT NULL \n"
        + ");";
    
    String createCPUTimePerformance = "CREATE TABLE IF NOT EXISTS cpu_time_performance (\n"
        + "cpu_number integer, \n"
        + "cpu_temp float NOT NULL, \n"
        + "clock_speed float NOT NULL, \n"
        + "timestamp text NOT NULL \n"
        + ");";
    
    try (Connection conn = DriverManager.getConnection(this.db);
        Statement stmt = conn.createStatement()) {
      // create the tables
      stmt.execute(createVolatile);
      stmt.execute(createPersistent);
      stmt.execute(createPersistentStats);
      stmt.execute(createNetworking);
      stmt.execute(createCPUInfo);
      stmt.execute(createCPUInterrupts);
      stmt.execute(createCPUTimePerformance);
      System.out.println("Tables created successfully.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  private void executeSQL(String sql){
  try (Connection conn = DriverManager.getConnection(this.db);
        Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
 }

  public void addStaticCPUMetrics(HashMap metrics){
     String db = "jdbc:sqlite:linux_metrics.db";
     ArrayList<String> cpu_num  = (ArrayList<String>) metrics.get("cpu_number");
     ArrayList<String> max_clock_rate = (ArrayList<String>) metrics.get("max_clock_rate");
     ArrayList<String> max_temp = (ArrayList<String>) metrics.get("max_temp");
     for(int index = 0; index < cpu_num.size(); index++){
     String insertCPUInfo = "INSERT INTO cpu_info values( "+
                              cpu_num.get(index) +" , "+
                              max_clock_rate.get(index) + " , "+
                              max_temp.get(index) + ");";


     executeSQL(insertCPUInfo);
     }

  }
   

   public void addCPUInterrupts(HashMap metrics){
   String db = "jdbc:sqlite:linux_metrics.db";
    ArrayList<String> cpu_number = (ArrayList<String>) metrics.get("cpu_number");
      ArrayList<String> interrupt_type = (ArrayList<String>) metrics.get("interrupt_type");
      ArrayList<String> interrupt_count = (ArrayList<String>) metrics.get("interrupt_count");
      ArrayList<String> date_time = (ArrayList<String>) metrics.get("date_time");
  
      for(int index = 0; index < cpu_number.size(); index++){
      String insertCPUInterrupts = "INSERT INTO cpu_interrupts values( "+ 
                              cpu_number.get(index) +" , '"+ 
                              interrupt_type.get(index) + "' , '"+
                              interrupt_count.get(index) + "' , '"+
                              date_time.get(index) + "');";
 
      executeSQL(insertCPUInterrupts);
      }

   }


     public void addCPUTimePerformance(HashMap metrics){
    ArrayList<String> cpu_number = (ArrayList<String>) metrics.get("cpu_number");
      ArrayList<String> current_clock_rate = (ArrayList<String>) metrics.get("current_clock_rate");
      ArrayList<String> current_temp = (ArrayList<String>) metrics.get("current_temp");
      ArrayList<String> date_time = (ArrayList<String>) metrics.get("date_time");

      for(int index = 0; index < cpu_number.size(); index++){
      String insertCPUPerformance = "INSERT INTO cpu_time_performance values( " +
                              cpu_number.get(index) +" , "+ 
                              current_clock_rate.get(index) + " , "+
                              current_temp.get(index) + " , '"+
                              date_time.get(index) + "');";

      executeSQL(insertCPUPerformance);
      }

   }


    public void addNetworkData(HashMap metrics){
       ArrayList<String> local_ip = (ArrayList<String>) metrics.get("local_ip");
   ArrayList<String> foreign_ip = (ArrayList<String>) metrics.get("foreign_ip");
   ArrayList<String> pid = (ArrayList<String>) metrics.get("pid");
   ArrayList<String> program_name = (ArrayList<String>) metrics.get("program_name");
   ArrayList<String> date_time = (ArrayList<String>) metrics.get("date_time");

     for(int index = 0; index < pid.size(); index++){
    String insertNetworking = "INSERT INTO networking_stats values( " +
                              pid.get(index) + " , '" + 
                              local_ip.get(index) +"' , '"+ 
                              foreign_ip.get(index) + "' , '"+
                              program_name.get(index) + "' , '"+
                              date_time.get(index) + "');";
     executeSQL(insertNetworking);
      }
   }

  public void addVolatileStats(HashMap metrics){
    ArrayList<String> total_memory = (ArrayList<String>) metrics.get("total_memory");
    ArrayList<String> available_memory = (ArrayList<String>) metrics.get("available_memory");
    ArrayList<String> total_swap = (ArrayList<String>) metrics.get("total_swap");
    ArrayList<String> swap_available = (ArrayList<String>) metrics.get("swap_available");
    ArrayList<String> date_time = (ArrayList<String>) metrics.get("date_time");
    for(int index = 0; index < total_memory.size(); index++){
    String insertVolatile = "INSERT INTO volatile_mem_stats values( "+
                              total_memory.get(index) + " , " +
                              available_memory.get(index) +" , "+
                              total_swap.get(index) + " , "+
                              swap_available.get(index) + " , '"+
                              date_time.get(index) + "');";
    executeSQL(insertVolatile);
    }
  }

 public void addPersistentStorage(HashMap metrics){
    ArrayList<String> disk_name = (ArrayList<String>) metrics.get("disk_name");
    ArrayList<String> used = (ArrayList<String>) metrics.get("used");
    ArrayList<String> available = (ArrayList<String>) metrics.get("available");
    for(int index = 0; index < disk_name.size(); index++){
        String insertPersistent = "INSERT INTO persistent_storage values( '"+ 
                                   disk_name.get(index)  + "' , " +
                                   (Float.parseFloat(used.get(index)) + Float.parseFloat(available.get(index))) + ");";
    executeSQL(insertPersistent);
    }

 }

  public void addPersistentStorageStats(HashMap metrics){
    ArrayList<String> disk_name = (ArrayList<String>) metrics.get("disk_name");
    ArrayList<String> used = (ArrayList<String>) metrics.get("used");
    ArrayList<String> available = (ArrayList<String>) metrics.get("available");
    ArrayList<String> used_percent =(ArrayList<String>) metrics.get("used_percent");
    
    for(int index = 0; index < disk_name.size(); index++){
        String insertPersistent = "INSERT INTO persistent_storage_stats values( '"+
                                   disk_name.get(index) + "' , " +
                                   used.get(index) + " , " +
                                   available.get(index) + " , '" +
                                   new Date().toString() +"' , " +
                                   used_percent.get(index) +  ");";
    executeSQL(insertPersistent);
    }
  }

}

