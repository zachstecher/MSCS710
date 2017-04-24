/**
 * Application: Metrics Reader for Linux OS
 * Final project for MCSC710
 * Prof. Anthony Giorgio
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * Entry point for the application.
 */

public class Main {
  public static void main(String[] args) throws InterruptedException{
    DBInterface dbi = new DBInterface();
    dbi.createNewDatabase();
    Thread.sleep(100);
    MetricsAggregator ma = new MetricsAggregator(1000);
    ma.repeatingEntries();
    
  }
}
