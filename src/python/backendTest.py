from requests import get
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('-ip', default = '100.40.189.190', type = str, help='IP Address of server')
args = parser.parse_args()
print("\n+++++++ PHP BACKEND TESTING +++++++++\n")
#Test numHDs:
##There are no arguments accepted for this script
r = get("http://%s/%s"%(args.ip,"numHDs.php"))
cleaned = r.text[:-2].replace("-","/").split("\n")
correctAnswer = ['/dev/nvme0n1p1', '/dev/nvme0n1p']
errors = 0
for response in cleaned:
	if(response not in correctAnswer):
		errors +=1

print("For numHDs.php there were %d errors"%errors)
##################################################
#Test numNetworkConns.php
r = get("http://%s/%s"%(args.ip,"numNetworkConns.php"))
cleaned = r.text[:-2].replace("-","/").split("\n")
correctAnswer = ['/dev/nvme0n1p1', '/dev/nvme0n1p']
errors = (r.text.split("|")[0] != '11,Sat Apr 15 14:37:30 EDT 2017')
print("For numNetworkConns.php there were %d errors"%errors)
#################################################
#Test numCpus.php
r = get("http://%s/%s"%(args.ip,"numCpus.php"))
cleaned = r.text[:-1]
errors = (r.text[:-1] != '12')
print("For numCpus.php there were %d errors"%errors)
################################################
#getData.php
r = get("http://%s/%s"%(args.ip,"getData.php"))
#For each table, test the appropriate response:
tableDict = {'cpu_info':'0,3.6,100','volatile_mem_stats':'65071340,65885900,67017724,67017724,Sat Apr 15 14:37:30 EDT 2017','persistent_storage':'/dev/nvme0n1p2,170377744','persistent_storage_stats':'/dev/nvme0n1p2,11676032,158701712,Sat Apr 15 14:37:30 EDT 2017,7','networking_stats':'1112,127.0.1.1:53,0.0.0.0:*,dnsmasq,Sat Apr 15 14:37:30 EDT 2017','cpu_interrupts':'0,Rescheduling Interrupt,2037,Sat Apr 15 14:37:26 EDT 2017','cpu_time_performance':'0,1614,22,Sat Apr 15 14:37:26 EDT 2017'}

errors = 0
for tableName in tableDict.keys():
	r = get("http://100.40.189.190/getData.php",{'table':str(tableName)})
	if(r.text.split("\n")[0] != tableDict[tableName]):
		errors+=1
		print("getData.php has an error when making a request for the %s table"%tableName)

print("Total getData.php errors %d"%errors)
print("\n++++ End of backend regression testing +++++\n")

