//Copy of Metrics Aggregator so we can work concurrently
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MetricsAggregator1 {
  
  MetricsAggregator1(){
    System.out.println("MetricsAggregator");
  }
  
  public HashMap getStaticCPUMetrics(){
    HashMap metrics = new HashMap();
    ArrayList<String> data = Utils.readFile("/proc/cpuinfo");
    ArrayList<String> cpuNumber = new ArrayList<>();
    ArrayList<String> maxClockRate = new ArrayList<>();
    ArrayList<String> maxTemp = new ArrayList<>();
    ArrayList<String> tempInfo = Utils.execShell("sensors");
    ArrayList<String>[] lists = new ArrayList[2]; 
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
     Pattern p = Pattern.compile("[0-9]+.[0-9]");
     Matcher matcher = p.matcher(line);
     String value = "";
     while(matcher.find()){
       value = matcher.group();
     }
     System.out.println(value);
     maxTemp.add(value);
     maxTemp.add(value);
    }


   metrics.put("cpu_number", cpuNumber);
   metrics.put("max_clock_rate", maxClockRate);
   metrics.put("max_temp",maxTemp);
   return metrics;
  }
}
