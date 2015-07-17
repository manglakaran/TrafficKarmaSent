import requests
import json,time
from decimal import Decimal
import csv,simplejson,urllib
from time import gmtime, strftime
from dateutil.tz import *
from datetime import datetime

key="AmM4rgqNbTsPBp9L5tzNmAeDyvW5BtQV9oPXt33TX6rJAQc469kIYLfprptuTguj" #insert key here
ofile  = open("trafficOutput_tofrom_Mumbai.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["LOCATION1","LOCATION2","LAT1","LNG1","LAT2","LNG2","LAT1_NEAR","LNG1_NEAR","LAT2_NEAR","LNG2_NEAR","Speed","Condition","TIME"])
ofile.close()

while(1):
	
	with open('Mumbai_tofrom_near.csv', 'rb') as csvfile:
		reader = csv.DictReader(csvfile)
		for row in reader:
			origin  = row["LAT1_NEAR"] + "," + row["LNG1_NEAR"]
			destination = row["LAT2_NEAR"] + "," + row["LNG2_NEAR"]
			print origin , destination
			local = tzlocal()
			now = datetime.now()
			now = now.replace(tzinfo = local)
			print now
			
			try:
				print "going"
				url_g = "http://dev.virtualearth.net/REST/V1/Routes?wp.0=" + origin + "&wp.1=" + destination +"&optmz=timeWithTraffic&key="+key
				print url_g
				result= simplejson.load(urllib.urlopen(url_g))
				dur_traffic =  result['resourceSets'][0]['resources'][0]['travelDurationTraffic']
				state=  result['resourceSets'][0]['resources'][0]['trafficCongestion']
				distance =  float(result['resourceSets'][0]['resources'][0]['travelDistance'])
				#duration = result['resourceSets'][0]['resources'][0]['travelDuration']
				speed = (distance/dur_traffic)*3600
				ofile  = open("trafficOutput_tofrom_Mumbai.csv", "a+b")
				c = csv.writer(ofile)
				
				c.writerow([row["LOCATION1"],row["LOCATION2"],row["LAT1"],row["LNG1"],row["LAT2"],row["LNG2"],row["LAT1_NEAR"],row["LNG1_NEAR"],row["LAT2_NEAR"],row["LNG2_NEAR"],speed,state,str(now)[0:-13] ])
				ofile.close()
			except Exception,e: 
				print "exception occured in fetching going"
				print str(e)
		print "csv write succesful!"	
			
		
		print "sleeping"
		time.sleep(1200)


