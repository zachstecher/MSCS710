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
  
  
  /*
   *  These methods each test a getMetrics method individually
   *  and throw an exception if one attempts to return a hashmap
   *  with null or missing values.
   */
  
  public static void RAMTest() {
    
    System.out.println("\n*******************************************");
    System.out.println("Beginning RAMMetrics Test.");
    System.out.println("*******************************************\n");
    RAMMetrics rmiTest = new RAMMetrics();
    try {
      HashMap<String, ArrayList> test = rmiTest.getRAMMetrics();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("RAMMetrics Initialization Success.");   
    } catch (IllegalArgumentException e) {
      System.out.println("RAMMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending RAMMetrics Test.");
    System.out.println("*******************************************\n");
  }
  
  public static void diskTest() {
  
    System.out.println("\n*******************************************");
    System.out.println("Beginning diskMetrics Test.");
    System.out.println("*******************************************\n");
    DiskMetrics diskTest = new DiskMetrics();
    try {
      HashMap<String, ArrayList> test = diskTest.getDiskMetrics();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("DiskMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("DiskMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending diskMetrics Test.");
    System.out.println("*******************************************\n");
  }
  
  public static void CPUTest() {
    CPUMetrics cpuStaticTest = new CPUMetrics();
    System.out.println("\n*******************************************");
    System.out.println("Beginning CPUMetrics Test.");
    System.out.println("*******************************************\n");
    try {
      HashMap<String, ArrayList> test = cpuStaticTest.getStaticCPUMetrics();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("StaticCPUMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("StaticCPUMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending CPUMetrics Test.");
    System.out.println("*******************************************\n");
  }
  
  public static void CPUInterruptsTest() {
    CPUMetrics cpuIntTest = new CPUMetrics();
    System.out.println("\n*******************************************");
    System.out.println("Beginning CPUInterrupts Test.");
    System.out.println("*******************************************\n");
    try {
      HashMap<String, ArrayList> test = cpuIntTest.getCPUInterrupts();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("CPUInterrupts Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("CPUInterrupts Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending CPUInterrupts Test.");
    System.out.println("*******************************************\n");
  }
  
  public static void CPUPerformanceTest() {
    System.out.println("\n*******************************************");
    System.out.println("Beginning CPUPerformance Test.");
    System.out.println("*******************************************\n");
    CPUMetrics cpuPerfTest = new CPUMetrics();
    try {
      HashMap<String, ArrayList> test = cpuPerfTest.getCPUTimePerformance();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("CPUTimePerformance Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("CPUTimePerformance Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending CPUPerformance Test.");
    System.out.println("*******************************************\n");
  }
  
  public static void networkTest() {
    NetworkMetrics netTest = new NetworkMetrics();
    System.out.println("\n*******************************************");
    System.out.println("Beginning NetworkMetrics Test.");
    System.out.println("*******************************************\n");
    try {
      HashMap<String, ArrayList> test = netTest.getNetworkMetrics();
      for(ArrayList item : test.values()) {
        if (item == null | item.isEmpty()) throw new IllegalArgumentException("Entry cannot be null.");
      }
      System.out.println("NetworkMetrics Initialization Success.");
    } catch (IllegalArgumentException e) {
      System.out.println("NetworkMetrics Initialization failed.");
      System.out.println(e);
      TestSuite.errors++;
    }
    System.out.println("\n*******************************************");
    System.out.println("Ending NetworkMetrics Test.");
    System.out.println("*******************************************\n");
  }
  
  
  /*
   *  This method tests the method to create a new database, and then
   *  tests each individual addMetrics method individually, and throws
   *  an error if the program tries to put the wrong information into a
   *  table, or if an entry is missing. This ensures the correct methods
   *  INSERT into the correct tables.
   */
   
  public static void DBTest() {
    System.out.println("\n*******************************************");
    System.out.println("Beginning DBInterface Test.");
    System.out.println("*******************************************\n");
    DBInterface dbi = new DBInterface();
    dbi.createNewDatabase();
    
    RAMMetrics rmiTest = new RAMMetrics();
    DiskMetrics diskTest = new DiskMetrics();
    CPUMetrics cpuTest = new CPUMetrics();
    NetworkMetrics netTest = new NetworkMetrics();
    
    
    HashMap test = rmiTest.getRAMMetrics();
    HashMap test1 = cpuTest.getStaticCPUMetrics();
    HashMap test2 = diskTest.getDiskMetrics();
    HashMap test3 = cpuTest.getCPUInterrupts();
    HashMap test4 = cpuTest.getCPUTimePerformance();
    HashMap test5 = netTest.getNetworkMetrics();
    HashMap test6 = diskTest.getDiskMetrics();
    
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
    System.out.println("\n*******************************************");
    System.out.println("Ending DBInterface Test.");
    System.out.println("*******************************************\n");
    System.out.println("\nTotal Number of Java Errors : " + TestSuite.errors + "\n");
    System.out.println("End of Java Testing");
  }
  
  
  /*
   *  This method tests the MetricsAggregator method for varying levels
   *  of recording frequency.
   */
   
}
