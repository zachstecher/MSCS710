javac src/java/TestSuite.java src/java/CPUMetrics.java src/java/DBInterface.java src/java/DiskMetrics.java src/java/MetricsAggregator.java src/java/NetworkMetrics.java src/java/RAMMetrics.java src/java/Utils.java

cd src/java
java -classpath ".:sqlite-jdbc-3.16.1.jar" TestSuite


cd ../python/
python3 backendTest.py
