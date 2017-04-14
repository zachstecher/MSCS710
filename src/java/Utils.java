import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
public static BufferedReader br = null;
public static FileReader fr = null;


/*
 * This method reads a file at the designated path
 * and returns it's contents as an array string.
 * 
 * Parameters: a file path including the name of the file to be read.
 */
  public static ArrayList<String> readFile(String path){
    ArrayList<String> lines = new ArrayList<>(); 
    try {
      fr = new FileReader(path);
      br = new BufferedReader(fr);
      String sCurrentLine;
      br = new BufferedReader(new FileReader(path));
      while ((sCurrentLine = br.readLine()) != null) {
        lines.add(sCurrentLine);
      }
    } catch (IOException e) {
        e.printStackTrace();
      } finally {
          try {
            if (br != null)
              br.close();
            if (fr != null)
              fr.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        } 
    return lines;
  } 


  public static ArrayList<String> execShell(String command){
    ArrayList<String> lines = new ArrayList<>();
    String s = null;
    try {
      Process p = Runtime.getRuntime().exec(command);
      BufferedReader stdInput = new BufferedReader(new 
          InputStreamReader(p.getInputStream()));
      BufferedReader stdError = new BufferedReader(new 
          InputStreamReader(p.getErrorStream()));
      while ((s = stdInput.readLine()) != null) {
        lines.add(s);
      }
      while ((s = stdError.readLine()) != null) {
        //std error
        System.out.println(s);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    return lines;
  } 
  
  public static String getValue(String line, String key, String valuePatter){
    Pattern pKey = Pattern.compile(key);
    String result = "";
    Matcher match = pKey.matcher(line);
    while(match.find()){
      result = match.group();
    }
        
    if(result != ""){
      Pattern pValue = Pattern.compile(valuePatter);
      Matcher matcher = pValue.matcher(line);
      String value = "";
      while(matcher.find()){
        value = matcher.group();
      }
      return value;
    } else {
      return null;
    }     
  }



    public static ArrayList<String>  getValues(String line, String key, String valuePatter){
    Pattern pKey = Pattern.compile(key);
    String result = "";
    Matcher match = pKey.matcher(line);
    while(match.find()){
      result = match.group();
    }

    if(result != ""){
      Pattern pValue = Pattern.compile(valuePatter);
      Matcher matcher = pValue.matcher(line);
      ArrayList<String> value = new ArrayList();
      while(matcher.find()){
        value.add(matcher.group());
      }
      return value;
    } else {
      return new ArrayList<>();
    }
  }}
