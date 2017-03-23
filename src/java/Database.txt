CREATE TABLE Volatile(
 mem_module_num int NOT NULL
 total_memory decimal(18,0)
 speed        float
 PRIMARY KEY(mem_module_num)
)


CREATE TABLE volatile_mem_time_stats(
 mem_module_num int NOT NULL
 mem_available  decimal(18,0)
 mem_used   decimal(18,0)
 swap_used decimal(18,0)
 timestamp datetime
 FOREIGN KEY (mem_module_num) REFERENCES Volatile(mem_module_num)       
)


CREATE TABLE persistent_storage(
 disk_num int
 total_size decimal(18,0)
 PRIMARY KEY(disk_num)
)

CREATE TABLE persistent_storage_stats(
 disk_num int 
 space_available decimal
 timestamp datetime
 FOREIGN KEY (disk_num) REFERENCES persistent_storage(disk_num)
)

CREATE TABLE networking(
 pid int
 open_port int
 user_space decimal(18,0)
)

CREATE TABLE cpu_interrupts(
 cpu_number int NOT NULL
 interrupt_process int
 count int
 timestamp datetime
FOREIGN KEY(cpu_number) REFERENCES cpu_interrupts(cpu_number)
 )

CREATE TABLE cpu_info(
cpu_number int NOT NULL
max_clock_rate decimal
max_temp decimal
PRIMARY KEY(cpu_number)
)

CREATE TABLE cpu_time_performance(
 cpu_number int NOT NULL
 cpu_temp decimal
 clock_speeds decimal
 num_threads int
 timestamp datetime
FOREIGN KEY(cpu_number) REFERENCES cpu_interrupts(cpu_number)
)