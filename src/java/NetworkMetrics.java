/**
 * Class: NetworkMetrics
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class holds the functionality needed to
 * read and format the computer's Network metrics
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;

public class NetworkMetrics{

  public static Map getNetworkMetrics(){
   String networkInfoCall = "sudo netstat -nlp";
   List<String> data = Utils.execShell(networkInfoCall);
   Map<String, List> metrics = new HashMap<>();
   List<String> localIP = new ArrayList();
   List<String> foreignIP = new ArrayList();
   List<String> pid = new ArrayList();
   List<String> programName = new ArrayList();
   List<String> dateTimes = new ArrayList();
   String r1 = "[0-9:]+\\.[0-9:]+\\.[0-9:]+\\.[0-9:]+:[0-9*]+";
   String r2 = "[:]+[0-9:]+[0-9:]+[0-9:*]+";
   
   // Read through the data and pull the information we're seeking
   for (String line: data){
     List<String> lineResult = Utils.getValues(line, "\\b(?:udp|tcp6|tcp|udp6)\\b", "(?:"+r1+"|"+r2+"|[0-9]+/|[^/]+(?=/$|$))");
     if(lineResult.size()>0){
       localIP.add(lineResult.get(0));
       foreignIP.add(lineResult.get(1));
       pid.add(lineResult.get(2).substring(0,lineResult.get(2).length()-1 ));
       programName.add(lineResult.get(3).replace(" ", ""));
       dateTimes.add(new Date().toString());
     }
  }
   
  // Add all the data to the hash map
  metrics.put("local_ip", localIP);
  metrics.put("foreign_ip",foreignIP);
  metrics.put("pid", pid);
  metrics.put("program_name", programName);
  metrics.put("date_time", dateTimes);
  return metrics;
  }



}
