import csv
from pygeocoder import Geocoder
a = []
fo = open("left.txt","r")
ofile  = open("Mumbai_tofrom.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["LOCATION1","LOCATION2","LAT1","LNG1","LAT2","LNG2"])
ofile.close()
for i in fo.readlines():
	first =  i.split("\t")[0] + ", Mumbai" 
	second =  i.split("\t")[1][0:-1] + ", Mumbai"
	#print first , second 

	
	result1 = Geocoder.geocode(first)
	result2 = Geocoder.geocode(second)
	print "[" , result1[0].coordinates[0] , "," , result1[0].coordinates[1], "],"
	ofile  = open("Mumbai_tofrom.csv", "a+b")
	c = csv.writer(ofile)
	c.writerow([first,second,result1[0].coordinates[0],result1[0].coordinates[1],result2[0].coordinates[0],result2[0].coordinates[1]])
	ofile.close()