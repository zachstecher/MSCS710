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
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class DiskMetrics {

  public static Map<String, List> getDiskMetrics(){
    List<String> data = Utils.execShell("df");
    Map diskMetrics = new HashMap();
    List<String> diskName = new ArrayList();
    List<String> used = new ArrayList();
    List<String> available = new ArrayList();
    List<String> usedPercent = new ArrayList();
    List<String> dateTimes = new ArrayList();
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    
    //Read through the data and pull out the specific information using RegEx
    for (String line: data){
      List<String> lineResult = Utils.getValues(line, "\\/dev.*?(?= )", " [0-9]+");
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
