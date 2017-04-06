import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Utils {
public static BufferedReader br = null;
public static FileReader fr = null;

public static ArrayList<String> readFile(String path){
    ArrayList<String> lines = new ArrayList<>(); 
    try {
	fr = new FileReader(FILENAME);
	br = new BufferedReader(fr);
	String sCurrentLine;
	br = new BufferedReader(new FileReader(FILENAME));
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
}
