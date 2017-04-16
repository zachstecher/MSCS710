import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class NetworkMetrics{

  public HashMap getNetworkMetrics(){
   ArrayList<String> data = Utils.execShell("sudo netstat -nlp");
   HashMap<String, ArrayList> metrics = new HashMap<String, ArrayList>();
   ArrayList<String> localIP = new ArrayList();
   ArrayList<String> foreignIP = new ArrayList();
   ArrayList<String> pid = new ArrayList();
   ArrayList<String> programName = new ArrayList();
   ArrayList<String> dateTimes = new ArrayList();
   String r1 = "[0-9:]+\\.[0-9:]+\\.[0-9:]+\\.[0-9:]+:[0-9*]+";
   String r2 = "[:]+[0-9:]+[0-9:]+[0-9:*]+";
   for (String line: data){
     ArrayList<String> lineResult = Utils.getValues(line, "\\b(?:udp|tcp6|tcp|udp6)\\b", "(?:"+r1+"|"+r2+"|[0-9]+/|[^/]+(?=/$|$))");
     if(lineResult.size()>0){
       localIP.add(lineResult.get(0));
       foreignIP.add(lineResult.get(1));
       pid.add(lineResult.get(2).substring(0,lineResult.get(2).length()-1 ));
       programName.add(lineResult.get(3).replace(" ", ""));
       dateTimes.add(new Date().toString());
     }
  }
  metrics.put("local_ip", localIP);
  metrics.put("foreign_ip",foreignIP);
  metrics.put("pid", pid);
  metrics.put("program_name", programName);
  metrics.put("date_time", dateTimes);
  return metrics;
  }



}
