import java.sql.*;

public class Main {
  public static void main(String[] args) {
    DBInterface dbi = new DBInterface();
    MetricsAggregator magr = new MetricsAggregator();
    Utils util = new Utils();
        
    dbi.createNewTable();
    dbi.addData();
    dbi.selectAll();
    
  }
}
