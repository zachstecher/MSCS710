import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class MetricsAggregator {
  
  public static void main(String[] args) {
    MetricsAggregator m = new MetricsAggregator();
    m.getRAMMetrics();
  }
  
  /*
   * This method gets the static CPU metrics of the computer from
   * the procfs file and inserts them into a hash map to eventually be
   * placed into a SQLite database.
   * 
   * Dependencies: Utils.java must be invoked for the readFile,
   *  execShell, and getValue methods
   * Parameters: None
   */
 
 //PLACEHOLDER
 
  
  /*
   * This method gets the disk statistics from the appropriate
   * procfs file and inserts them into an ArrayList.
   * 
   * Parameters: None
   */
  
  public HashMap getRAMMetrics() {
    HashMap ramMetrics = new HashMap();
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    ArrayList<String> data = Utils.readFile("/proc/meminfo");
    ArrayList<String> totalSize = new ArrayList<>();
    ArrayList<String> spaceAvail = new ArrayList<>();
    ArrayList<String> swapSize = new ArrayList<>();
    ArrayList<String> swapAvail = new ArrayList<>();
    ArrayList<String>[] lists = new ArrayList[4];
    lists[0] = totalSize;
    lists[1] = spaceAvail;
    lists[2] = swapSize;
    lists[3] = swapAvail;
    
    for(String line : data){
    String result = "";
    String[] keys = {"MemAvail", "MemTotal", "SwapTotal", "SwapFree"};
    String[] patterns = {"[0-9]+","[0-9]+.[0-9]", "[0-9]+.[0-9]", "[0-9]+.[0-9]"};
      for (int i = 0; i < keys.length; i++){
        String result_sub  = Utils.getValue(line, keys[i], patterns[i]);
        if(result_sub != null){
          lists[i].add(result_sub);
        }
      }
    }
    
    
    ramMetrics.put("Total Memory", totalSize);
    ramMetrics.put("Available Memory", spaceAvail);
    ramMetrics.put("Total Swap", swapSize);
    ramMetrics.put("Swap Available", swapAvail);
    ramMetrics.put("Timestamp", timestamp);
    
    System.out.println(ramMetrics);
    return ramMetrics;
  }
}
