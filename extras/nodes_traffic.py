import requests
import json,time
from decimal import Decimal
import csv,simplejson,urllib
from time import gmtime, strftime
from dateutil.tz import *
import datetime

key="AmM4rgqNbTsPBp9L5tzNmAeDyvW5BtQV9oPXt33TX6rJAQc469kIYLfprptuTguj" #insert key here
ofile  = open("nodes_traffic_Mumbai.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["LAT1","LNG1","LAT2","LNG2","Distance","Speed","Condition","TIME"])
ofile.close()

while(1):
	
	with open('nodes.csv', 'rb') as csvfile:
		reader = csv.DictReader(csvfile)
		for row in reader:
			origin  = row["LAT1"] + "," + row["LNG1"]
			destination = row["LAT2"] + "," + row["LNG2"]
			print origin , destination
			now = datetime.datetime.now()
			print now.strftime("%A %H:%M %d-%m-%Y")
			time_now = now.strftime("%A %H:%M %d-%m-%Y")
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
				ofile  = open("nodes_traffic_Mumbai.csv", "a+b")
				c = csv.writer(ofile)
				
				c.writerow([row["LAT1"],row["LNG1"],row["LAT2"],row["LNG2"],distance,speed,state,time_now])
				ofile.close()
			except Exception,e: 
				print "exception occured in fetching going"
				print str(e)
		print "csv write succesful!"	
			
		
		print "sleeping"
		time.sleep(1200)


