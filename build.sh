#!/bin/bash
web=$1
java=$2
run=$3
serverDir=/var/www/html/

# --- Web application component --- #
if [ $web == "build" ]
 then
	#Create the server
	sudo apt-get install apache2
	sudo apt install php libapache2-mod-php
	sudo apt install php-cli

	#Remove any old files from the server
	sudo rm ${serverDir}*

	#Copy all new files to the server
	sudo cp src/javascript/* ${serverDir}
	sudo cp src/php/* ${serverDir}

	#Start the server
	sudo systemctl restart apache2.service
fi;
# --- Comile the build 

if [ $java == "build" ]
then
	#Compile java
	find -name "*.java" > uncompiled.txt
	javac -classpath ".;src/java/sqlite-jdbc-3.16.1.jar;" @uncompiled.txt
        rm uncompiled.txt
        

fi

if [ $run == "true" ]
then
	#Run the java program
        cd src/java
        java -cp .:sqlite-jdbc-3.16.1.jar Main
	
	#Start the server
        sudo systemctl restart apache2.service
fi

