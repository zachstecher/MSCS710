/**
 * Class: CPUMetrics
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class contains the methods that read the CPU
 * metrics from the system and format them into a hash
 * map to be inserted to the database.
 */
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;


public static class CPUMetrics{


  /*
   * This method collects the static information about each CPU
   * This will only be collected once the first time the application
   * is run as these numbers should never change.
   */
  public static Map getStaticCPUMetrics(){
    Map<String, ArrayList> metrics = new HashMap<String, ArrayList>();
    List<String> data = Utils.readFile("/proc/cpuinfo");
    List<String> cpuNumber = new ArrayList<>();
    List<String> maxClockRate = new ArrayList<>();
    List<String> maxTemp = new ArrayList<>();
    List<String> tempInfo = Utils.execShell("sensors");
    List<String>[] lists = new ArrayList[2];
    lists[0] = cpuNumber;
    lists[1] = maxClockRate;



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
      boolean usableLine = line.contains("Core");
      if(usableLine){
        Pattern p = Pattern.compile("[0-9]+.[0-9]");
        Matcher matcher = p.matcher(line);
        String value = "";
        while(matcher.find()){
          value = matcher.group();
        }
        maxTemp.add(value);
        maxTemp.add(value);
      }
    }
    
    // Add each metric to the hash map
    metrics.put("cpu_number", cpuNumber);
    metrics.put("max_clock_rate", maxClockRate);
    metrics.put("max_temp",maxTemp);
    return metrics;
    }


  public static Map getCPUInterrupts(){
    Map<String, ArrayList> metrics = new HashMap<String, ArrayList>();
    /*Metrics for:
     * Function call interrupts; key: CAL:
     * Rescheduling Interrupts;  key: RES:
     */

    String interruptsPath = "/proc/interrupts";
    List<String> data = Utils.readFile(interruptsPath);
    List<String> cpuNumber = new ArrayList<>();
    List<String> interruptType = new ArrayList();
    List<String> interruptCount = new ArrayList();
    List<String> timestamp = new ArrayList();
    for(String line: data){
      ArrayList<String> resInterrupts_sub = Utils.getValues(line, "RES:", "[0-9]+");
      for(int index = 0; index < resInterrupts_sub.size(); index++){
        cpuNumber.add(index+"");
        interruptType.add("Rescheduling Interrupt");
        interruptCount.add(resInterrupts_sub.get(index));
        timestamp.add(new Date().toString());
      }
    }
    for(String line: data){
      ArrayList<String> funcInterrupts_sub = Utils.getValues(line, "CAL:", "[0-9]+");
      for(int index = 0; index < funcInterrupts_sub.size(); index++){
        cpuNumber.add(index+"");
        interruptType.add("Function Call Interrupt");
        interruptCount.add(funcInterrupts_sub.get(index));
        timestamp.add(new Date().toString());
        
      }
      
    }
    
    // Add each metric to the hash map
    metrics.put("cpu_number", cpuNumber);
    metrics.put("interrupt_type", interruptType);
    metrics.put("interrupt_count",interruptCount);
    metrics.put("date_time", timestamp);
    return metrics;
  }

  /*
   * This method collects the real time performance metrics
   * from each CPU and formats them to be inserted into the
   * database.
   */
  public HashMap getCPUTimePerformance(){
    HashMap<String, ArrayList> metrics = new HashMap<String, ArrayList>();
    ArrayList<String> data = Utils.readFile("/proc/cpuinfo");
    ArrayList<String> cpuNumber = new ArrayList<>();
    ArrayList<String> clockRate = new ArrayList<>();
    ArrayList<String> curTemp = new ArrayList<>();
    ArrayList<String> dateTimes = new ArrayList<>();
    ArrayList<String> tempInfo = Utils.execShell("sensors");
    ArrayList<String>[] lists = new ArrayList[2];
    lists[0] = cpuNumber;
    lists[1] = clockRate;
    
    for(String line : data){
      String result = "";
      String[] keys = {"processor", "cpu MHz"};
      String[] patterns = {"[0-9]+","[0-9]+.[0-9]"};
      for (int i = 0; i < keys.length; i++){
        String result_sub  = Utils.getValue(line, keys[i], patterns[i]);
        if(result_sub != null){
          if(i == 0){
            dateTimes.add(new Date().toString());
          }
          lists[i].add(result_sub);
        }
      }
    }

    for(String line : tempInfo){
      boolean usableLine = line.contains("Core");
      if(usableLine){
        Pattern p = Pattern.compile("[0-9]+.[0-9]");
        Matcher matcher = p.matcher(line);
        String value = "";
        matcher.find();
        value = matcher.group();
        curTemp.add(value);
        curTemp.add(value);
      }
    }
    
    // Add each metric to the hashmap
    metrics.put("cpu_number", cpuNumber);
    metrics.put("current_clock_rate", clockRate);
    metrics.put("current_temp", curTemp);
    metrics.put("date_time", dateTimes);
    return metrics;
  }
}
