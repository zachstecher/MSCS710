/**
 * Class: MetricsAggregator
 * 
 * @authors Matthew Sokoloff, Zach Stecher, Rickin Adatia
 * 
 * This class handles aggregating all the different metrics
 * reading classes and methods and then adds them to the
 * database using the methods in DBInterface.
 */

public class MetricsAggregator {
  private int recordFrequency;
 
 
  public MetricsAggregator(int recordFrequency) {
    this.recordFrequency = recordFrequency;
    //One time entries:
    this.db.addStaticCPUMetrics(CPUMetrics.getStaticCPUMetrics());
    this.db.addPersistentStorage(DiskMetrics.getDiskMetrics());
  }
  
  public void repeatingEntries() throws InterruptedException {
   // For testing. Will eventually just run infinitely
   // while(true){
    int count = 0;
    
    while(count < 10000){
    //cpu metrics
    this.db.addCPUInterrupts(CPUMetrics.getCPUInterrupts());
    this.db.addCPUTimePerformance( CPUMetrics.getCPUTimePerformance());
    //network metrics
    this.db.addNetworkData(NetworkMetrics.getNetworkMetrics());
    //ram metrics
    this.db.addVolatileStats(RAMMetrics.getRAMMetrics());
    //disk metrics
    this.db.addPersistentStorageStats(DiskMetrics.getDiskMetrics());
    //wait...
    Thread.sleep(this.recordFrequency);
    count++;
    }
  }
}
