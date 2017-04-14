


CREATE TABLE volatile_mem_time_stats(
 mem_available  decimal(18,0)
 mem_total   decimal(18,0)
 swap_used decimal(18,0)
 swap_available decimal(18,0)
 timestamp datetime
 FOREIGN KEY (mem_module_num) REFERENCES Volatile(mem_module_num)       
)


CREATE TABLE persistent_storage(
 disk_name VARCHAR(64)
 total_size int
 PRIMARY KEY(disk_name)
)

CREATE TABLE persistent_storage_stats(
 disk_name VARCHAR(64)
 space_available decimal(18,0)
 used_percent int
 timestamp datetime
 FOREIGN KEY (disk_num) REFERENCES persistent_storage(disk_num)
)

CREATE TABLE networking(
 pid int
 local_ip varchar(64)
 foreign_ip varchar(64)
 program_name varchar(128)
 timestamp datetime 
)

CREATE TABLE cpu_interrupts(
 cpu_number int NOT NULL
 interrupt_type VARCHAR(128)
 interrupt_count VARCHAR(128)
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
 clock_speed decimal
 timestamp datetime
FOREIGN KEY(cpu_number) REFERENCES cpu_interrupts(cpu_number)
)
