

web=$1
java=$2
run=$3
serverDir=/var/www/html/
# --- Web application component --- #
if [web == "build"]
 then
	#Create the server
	sudo apt-get install apache2
	sudo apt install php libapache2-mod-php
	sudo apt install php-cli
	sudo systemctl restart apache2.service

	#Remove any old files from the server
	rm /var/www/html/* ${serverDir}

	#Copy all new files to the server
	cp src/javascript/* ${serverDir}
	cp src/php/* ${serverDir}

	#Start the server
	sudo systemctl restart apache2.service
fi;
# --- Comile the build 

if [java == "build"]
then
	#Compile java
	find -name "*.java" > uncompiled.txt
	javac @uncompiled.txt

fi

if [run == "true"]
then
	#Run the java program
	java src/java/Main
	
	#Start the server
        sudo systemctl restart apache2.service
fi


