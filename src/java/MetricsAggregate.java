

public class MetricsAggregator1{
 NetworkMetrics net;
 CPUMetrics cpu;
 DBInterface db;

 public MetricsAggregator1(){
    this.net = new NetworkMetrics();
    this.cpu = new CPUMetrics();
    this.db = new DBInterface();
    //One time entries:
    this.db. addfunction (this.cpu.getStaticCPUMetrics());
     
 }
  
public void repeatingEntries(){
   while(true){
     
     this.db.addfunction( this.cpu.getStaticCPUMetrics());
     this.db.addfunction( this.cpu.getCPUInterrupts());
     this.db.addfunction( this.cpu.getCPUTimePerformance());
     this.db.addfunction( this.net.getNetworkMetrics());
     Thread.sleep(1000);
   }

}
  cpu.getStaticCPUMetrics()
  cpu.getCPUInterrupts()
  cpu.getCPUTimePerformance()

}

