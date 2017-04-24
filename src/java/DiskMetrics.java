/**
 * Class: DiskMetrics
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class holds the functionality to read and arrange the
 * statistics of the computer's disk drive.
 */

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class DiskMetrics {
  public HashMap<String, ArrayList> getDiskMetrics(){
    ArrayList<String> data = Utils.execShell("df");
    HashMap diskMetrics = new HashMap();
    ArrayList<String> diskName = new ArrayList();
    ArrayList<String> used = new ArrayList();
    ArrayList<String> available = new ArrayList();
    ArrayList<String> usedPercent = new ArrayList();
    ArrayList<String> dateTimes = new ArrayList();
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    
    //Read through the data and pull out the specific information using RegEx
    for (String line: data){
      ArrayList<String> lineResult = Utils.getValues(line, "\\/dev.*?(?= )", " [0-9]+");
      if(lineResult.size()>0) {
        diskName.add(line.split(" ")[0]);
        used.add(lineResult.get(1).replace(" ", ""));
        available.add(lineResult.get(2).replace(" ", ""));
        usedPercent.add(lineResult.get(3).replace(" ", ""));
        dateTimes.add(new Date().toString());
      }
    
    }
    
    // Add all the metrics into the hash map
    diskMetrics.put("disk_name", diskName);
    diskMetrics.put("used", used);
    diskMetrics.put("available", available);
    diskMetrics.put("used_percent", usedPercent);
    diskMetrics.put("date_time", dateTimes);
    return diskMetrics;
  }
}
