/**
 * Class: RAMMetrics
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class contains the methods that read the RAM
 * metrics from the system and format them into a hash
 * map to be inserted to the database.
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.List;
import java.util.regex.Matcher;
import java.util.Date;

public class RAMMetrics {
  
  public static Map getRAMMetrics() {
    Map<String, List> ramMetrics = new HashMap<>();
    List<String> data = Utils.readFile("/proc/meminfo");
    List<String> totalSize = new ArrayList<>();
    List<String> spaceAvail = new ArrayList<>();
    List<String> swapSize = new ArrayList<>();
    List<String> swapAvail = new ArrayList<>();
    List<String> dateTimes = new ArrayList();
    List<String>[] lists = new ArrayList[4];
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
    dateTimes.add(new Date().toString()); // Timestamp
    
    // Add each metric to the hash map
    ramMetrics.put("total_memory", totalSize);
    ramMetrics.put("available_memory", spaceAvail);
    ramMetrics.put("total_swap", swapSize);
    ramMetrics.put("swap_available", swapAvail);
    ramMetrics.put("date_time", dateTimes);
    
    return ramMetrics;
  }
  
}
