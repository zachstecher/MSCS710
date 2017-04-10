import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    DBInterface dbi = new DBInterface();
    MetricsAggregator magr = new MetricsAggregator();
    Utils util = new Utils();
    
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    ArrayList<String> data = Utils.readFile("/proc/diskstats");
        
    //dbi.createNewTable();
    //dbi.addData();
    //dbi.selectAll();
    
  }
}
