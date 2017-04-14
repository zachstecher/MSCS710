//Copy of Metrics Aggregator so we can work concurrently
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class MetricsAggregator1 {
  
  MetricsAggregator1(){
    System.out.println("MetricsAggregator");
  }
  public static void main(String[] args){
MetricsAggregator1 m = new MetricsAggregator1();
System.out.println(m.getStaticCPUMetrics());
        //Does a for loop run in a length zero array in java?
System.out.println("+++++++++++++++++++++++++++");
System.out.println(m.getCPUInterrupts());
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


   metrics.put("cpu_number", cpuNumber);
   metrics.put("max_clock_rate", maxClockRate);
   metrics.put("max_temp",maxTemp);
   return metrics;
  }




  public HashMap getCPUInterrupts(){
	HashMap metrics = new HashMap();
        /*Metrics for:
		* Function call interrupts; key: CAL:
                * Rescheduling Interrupts;  key: RES:
        */


      ArrayList<String> data = Utils.readFile("/proc/interrupts");
      ArrayList<String> cpuNumber = new ArrayList<>();
      ArrayList<String> interruptType = new ArrayList();
      ArrayList<String> interruptCount = new ArrayList();
      ArrayList<String> timestamp = new ArrayList();
      Date date = new Date();
      String dateTime = date.toString();
      for(String line: data){
        String result = "";
        ArrayList<String> resInterrupts_sub = Utils.getValues(line, "RES:", "[0-9]+");
           for(int index = 0; index < resInterrupts_sub.size(); index++){
                cpuNumber.add(index+"");
                interruptType.add("Rescheduling Interrupt");
                interruptCount.add(resInterrupts_sub.get(index));
                timestamp.add(dateTime);     
           }
        ArrayList<String> funcInterrupts_sub = Utils.getValues(line, "CAL:", "[0-9]+");
           for(int index = 0; index < funcInterrupts_sub.size(); index++){
                cpuNumber.add(index+"");
                interruptType.add("Function Call Interrupt");
                interruptCount.add(funcInterrupts_sub.get(index));
                timestamp.add(dateTime);

	   }

        }
      metrics.put("cpu_number", cpuNumber);
      metrics.put("interrupt_type", interruptType);
      metrics.put("interrupt_count",interruptCount);
      metrics.put("date_time", dateTime);
      return metrics;
      }     


 }

