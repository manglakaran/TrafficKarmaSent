import requests
import json,time
from decimal import Decimal
import csv,simplejson,urllib

key="AtZN84PrOA1bOf8mIq0D0sLF8mHN8wE_erbArhbbL6SdJqjRlfyCEUUBopTSdFZ7" #bing-insert key here

key_kir = "AIzaSyBRh3X-kGRZknSQOySFVitucwpiYSgiu3o";
key_karan = "AIzaSyDuJ9cAenDLsuI3Lzwh698hxiD2r2wDpyw";
key = "AIzaSyBIH2gDxgWNFhQ8Dq4M50VOHNqzfiPSSGg";
key_kireet13="AIzaSyAQOjIILGSqTg2iq9kUaT1wbXxCItwRk-E";

ofile  = open("Mumbai_tofrom_near.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["LOCATION1","LOCATION2","LAT1","LNG1","LAT2","LNG2","LAT1_NEAR","LNG1_NEAR","LAT2_NEAR","LNG2_NEAR"])

ofile.close()

with open('Mumbai_tofrom.csv') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
		origin = row["LAT1"]  + "," + row["LNG1"]
		origin2 = row["LAT2"]  + "," + row["LNG2"]
		try:
			url_g = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + origin + "&type=transit_station&radius=500&key=" + key_karan;
			print url_g
			result= simplejson.load(urllib.urlopen(url_g))
			temp =  result['results']
			
			for i in xrange(0,len(temp)):
				str1 = temp[i]
				if "bus_station" in str1["types"]:
					lat1 = str1["geometry"]["location"]["lat"]
					lng1 = str1["geometry"]["location"]["lng"]
					break
			
			url_g = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + origin2 + "&type=transit_station&radius=500&key=" + key_karan;
			print url_g
			result= simplejson.load(urllib.urlopen(url_g))
			temp =  result['results']
			
			for i in xrange(0,len(temp)):
				str1 = temp[i]
				if "bus_station" in str1["types"]:
					lat2 = str1["geometry"]["location"]["lat"]
					lng2 = str1["geometry"]["location"]["lng"]
					break
					
					
			ofile  = open("Mumbai_tofrom_near.csv", "a+b")
			c = csv.writer(ofile)
			c.writerow([row["LOCATION1"],row["LOCATION2"],row["LAT1"],row["LNG1"],row["LAT2"],row["LNG2"],lat1,lng1,lat2,lng2])

			ofile.close()
			
		except Exception,e: 
			print "exception occured in fetching going"
			print str(e)
