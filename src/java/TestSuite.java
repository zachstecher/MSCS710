/**
 * Class: Testsuite
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class contains our custom test suite for this
 * program's java code. This class handles Unit Testing
 * and Integration Testing for this program's Java code.
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class TestSuite {
public static int errors = 0; // Error tracker
  
  // Initialize and run all of our test modules
  public static void main(String[] args) throws InterruptedException{
   System.out.println("+++++++ JAVA BACKEND TESTING +++++++++");
    TestSuite test1 = new TestSuite();
    test1.RAMTest();
    test1.diskTest();
    test1.CPUTest();
    test1.CPUInterruptsTest();
    test1.CPUPerformanceTest();
    test1.DBTest();
    //test1.MetricsAggregatorTest();
  }
  
  private static void printHeader(String m){
  System.out.println("\n*******************************************");
  System.out.println(m);
  System.out.println("*******************************************\n");
  }
  
  /*
   *  These methods each test a getMetrics method individually
   *  and throw an exception if one attempts to return a hashmap
   *  with null or missing values.
   */
  
  public static void RAMTest() {
    
    printHeader("Beginning RAMMetrics Test.");
    RAMMetrics rmiTest = new RAMMetrics();
    try {
      Map<String, List> test = rmiTest.getRAMMetrics();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("RAMMetrics Initialization Success.");   
    } catch (IllegalArgumentException e) {
      System.out.println("RAMMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending RAMMetrics Test.");
  }
  
  public static void diskTest() {
  
    printHeader("Beginning diskMetrics Test.");
    DiskMetrics diskTest = new DiskMetrics();
    try {
      Map<String, List> test = diskTest.getDiskMetrics();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("DiskMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("DiskMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending diskMetrics Test.");
  }
  
  public static void CPUTest() {
    CPUMetrics cpuStaticTest = new CPUMetrics();
    printHeader("Beginning CPUMetrics Test.");
    try {
      Map<String, List> test = cpuStaticTest.getStaticCPUMetrics();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("StaticCPUMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("StaticCPUMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending CPUMetrics Test.");
  }
  
  public static void CPUInterruptsTest() {
    CPUMetrics cpuIntTest = new CPUMetrics();
    printHeader("Beginning CPUInterrupts Test.");
    try {
      Map<String, List> test = cpuIntTest.getCPUInterrupts();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("CPUInterrupts Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("CPUInterrupts Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending CPUInterrupts Test.");
  }
  
  public static void CPUPerformanceTest() {
    printHeader("Beginning CPUPerformance Test.");
    CPUMetrics cpuPerfTest = new CPUMetrics();
    try {
      Map<String, List> test = cpuPerfTest.getCPUTimePerformance();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("CPUTimePerformance Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("CPUTimePerformance Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending CPUPerformance Test.");
  }
  
  public static void networkTest() {
    NetworkMetrics netTest = new NetworkMetrics();
    printHeader("Beginning NetworkMetrics Test.");
    try {
      Map<String, List> test = netTest.getNetworkMetrics();
      for(List item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("NetworkMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("NetworkMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    printHeader("Ending NetworkMetrics Test.");
  }
  
  
  /*
   *  This method tests the method to create a new database, and then
   *  tests each individual addMetrics method individually, and throws
   *  an error if the program tries to put the wrong information into a
   *  table, or if an entry is missing. This ensures the correct methods
   *  INSERT into the correct tables.
   */
   
  public static void DBTest() {
    printHeader("Beginning DBInterface Test.");
    DBInterface dbi = new DBInterface();
    dbi.createNewDatabase();
    
    RAMMetrics rmiTest = new RAMMetrics();
    DiskMetrics diskTest = new DiskMetrics();
    CPUMetrics cpuTest = new CPUMetrics();
    NetworkMetrics netTest = new NetworkMetrics();
    
    
    Map test = rmiTest.getRAMMetrics();
    Map test1 = cpuTest.getStaticCPUMetrics();
    Map test2 = diskTest.getDiskMetrics();
    Map test3 = cpuTest.getCPUInterrupts();
    Map test4 = cpuTest.getCPUTimePerformance();
    Map test5 = netTest.getNetworkMetrics();
    Map test6 = diskTest.getDiskMetrics();
    
    try {
      dbi.addVolatileStats(test);
      System.out.println("Volatile Metrics added to table.");
      test = cpuTest.getStaticCPUMetrics();
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("Volatile Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.executeSQL("DELETE FROM cpu_info;");
      dbi.addStaticCPUMetrics(test1);
      System.out.println("StaticCPU Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("StaticCPU Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.executeSQL("DELETE FROM persistent_storage;");
      dbi.addPersistentStorage(test2);
      System.out.println("PersistentStorage Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("PersistentStorage Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.addCPUInterrupts(test3);
      System.out.println("CPUInterrupts Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("CPUInterrupts Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.addCPUTimePerformance(test4);
      System.out.println("CPUTime Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("CPUTime Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.addNetworkData(test5);
      System.out.println("Network Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("Network Entry Failed! Table Entry Missing A Value.");
      System.out.println("");
      TestSuite.errors++;
    }
    
    try {
      dbi.addPersistentStorageStats(test6);
      System.out.println("PersistentStorageStats Metrics added to table.");
    } catch(NullPointerException e) {
      System.out.println("You're putting the wrong metrics in TestSuite table.");
      TestSuite.errors++;
    } catch(IndexOutOfBoundsException e) {
      System.out.println("PersistentStorageStats Entry Failed! Table Entry Missing A Value.");
      TestSuite.errors++;
      
    }
    printHeader("Ending DBInterface Test.");
    System.out.println("\nTotal Number of Java Errors : " + TestSuite.errors + "\n");
    System.out.println("End of Java Testing");
  }
  
  
  /*
   *  This method tests the MetricsAggregator method for varying levels
   *  of recording frequency.
   */
   
}
