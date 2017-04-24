#!/bin/bash
arg=$1
serverDir=/var/www/html/

# --- Web application component --- #
if [[ $arg == "configure" ]]
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

# --- Comile the code --- #
if [[ $arg == "compile" ]]
then
	printf "\n Compiling.....\n***************************"
	rm ${serverDir}*.db
	rm src/java/*.class
	printf "\nCleared old class files and leftover database"
	#Compile java
	find -name "*.java" > uncompiled.txt
	javac -classpath ".;src/java/sqlite-jdbc-3.16.1.jar;" @uncompiled.txt
        rm uncompiled.txt
	printf "\nFinished Compiling!\n***************************\n\n"
fi


# --- Run the java program and launch the web server --- #
if [[ $arg == "run" ]]
then
	#Run the java program
	printf "\n Running Java Program.....\n*************************\n"
        cd src/java
        java -cp .:sqlite-jdbc-3.16.1.jar Main
	printf "\n ********************\n Java Program Finished Executing... starting web server"
	#Start the server
        sudo systemctl restart apache2.service
	printf "\nApache Web Server Started!\n\n"
fi

