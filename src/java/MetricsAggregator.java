

public class MetricsAggregator{
 NetworkMetrics net;
 CPUMetrics cpu;
 DBInterface db;
 RAMMetrics ram;
 DiskMetrics disk;

 int recordFrequency;
 
 
 public MetricsAggregator(int recordFrequency){
    this.net = new NetworkMetrics();
    this.cpu = new CPUMetrics();
    this.db = new DBInterface();
    this.ram = new RAMMetrics();
    this.disk = new DiskMetrics();
    
    this.recordFrequency = recordFrequency;
    //One time entries:
    this.db.addStaticCPUMetrics(this.cpu.getStaticCPUMetrics());
    this.db.addPersistentStorage(this.disk.getDiskMetrics());
 }
  
public void repeatingEntries(){
   //For testing. Will eventually just run infinitely
//   while(true){
     int count = 0;
     while(count < 10){
     //cpu metrics
     this.db.addCPUInterrupts(this.cpu.getCPUInterrupts());
     this.db.addCPUTimePerformance( this.cpu.getCPUTimePerformance());
     //network metrics
     this.db.addNetworkData(this.net.getNetworkMetrics());
     //ram metrics
     this.db.addVolatileStats(this.ram.getRAMMetrics());
     //disk metrics
     this.db.addPersistentStorageStats(this.disk.getDiskMetrics());
     //wait...
     Thread.sleep(this.recordFrequency);
     count++;
   }
  }
}

