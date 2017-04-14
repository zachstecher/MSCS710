import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class RAMMetrics {
  public RAMMetrics() {
    
  }
  
  public HashMap getRAMMetrics() {
    HashMap ramMetrics = new HashMap();
    ArrayList<String> data = Utils.readFile("/proc/meminfo");
    ArrayList<String> totalSize = new ArrayList<>();
    ArrayList<String> spaceAvail = new ArrayList<>();
    ArrayList<String> swapSize = new ArrayList<>();
    ArrayList<String> swapAvail = new ArrayList<>();
    ArrayList<String> dateTimes = new ArrayList();
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
    dateTimes.add(new Date().toString());
    
    
    ramMetrics.put("total_memory", totalSize);
    ramMetrics.put("available_memory", spaceAvail);
    ramMetrics.put("total_swap", swapSize);
    ramMetrics.put("swap_available", swapAvail);
    ramMetrics.put("date_time", dateTimes);
    
    return ramMetrics;
  }
  
}
