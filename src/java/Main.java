import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    DBInterface dbi = new DBInterface();
    Utils util = new Utils();
    
    dbi.createNewDatabase();
    
  }
}
