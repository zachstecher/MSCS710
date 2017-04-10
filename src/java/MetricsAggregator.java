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
  MetricsAggregator(){
    System.out.println("MetricsAggregator");
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
  public HashMap getStaticCPUMetrics(){
    HashMap metrics = new HashMap();
    ArrayList<String> data = Utils.readFile("/proc/cpuinfo");
    ArrayList<String> cpuNumber = new ArrayList<>();
    ArrayList<String> maxClockRate = new ArrayList<>();
    ArrayList<String> max_Temp = new ArrayList<>();
    ArrayList<String> tempInfo = Utils.execShell("sensors");
    ArrayList<String>[] lists = {cpuNumber, maxClockRate};
    for(String line : data){
    String result = "";
    String[] keys = {"processor", "model name"};
    String[] patterns = {"[0-9]+","[0-9]+.[0-9]"};
       for (int i = 0; i < keys.length; i++){
       String result_sub  = Utils.getValue(line, keys[i], patterns[i]);
       if(result_sub != null){
          lists[i].add(result_sub);
       }
      }
    }

    for(String line : tempInfo){
     Pattern p = Pattern.compile("[0-9]+.[0-9]");
     Matcher matcher = p.matcher(line);
     String value = "";
     while(matcher.find()){
       value = matcher.group();
     }
     System.out.println(value);
     max_Temp.add(value);
     max_Temp.add(value);
    }


   metrics.put("cpu_number", cpuNumber);
   metrics.put("max_clock_rate", maxClockRate);
   metrics.put("max_temp", max_Temp);
  }
  
  /*
   * This method gets the disk statistics from the appropriate
   * procfs file and inserts them into an ArrayList.
   * 
   * Parameters: None
   */
  
  public HashMap getStorageMetrics() {
    HashMap storageMetrics = new HashMap();
    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    ArrayList<String> data = Utils.readFile("/proc/diskstats");
    ArrayList<String> diskNumber = new ArrayList<>();
    ArrayList<String> totalSize = new ArrayList<>();
    ArrayList<String> spaceAvail = new ArrayList<>();
    //ArrayList<String> timeStamp = timestamp;
    ArrayList<String>[] lists = {diskNumber, spaceAvail, totalSize};
  }
}

