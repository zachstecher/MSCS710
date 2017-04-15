import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) throws InterruptedException{
    DBInterface dbi = new DBInterface();
    dbi.createNewDatabase();
    Thread.sleep(100);
    MetricsAggregator ma = new MetricsAggregator(1000);
    ma.repeatingEntries();
    
  }
}
