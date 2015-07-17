import csv
from collections import namedtuple

fo = open("places.txt","r")

for i in fo.readlines():
	arr = i.split(" ")
	place_name = arr[0]
	avg = 0
	time_count = 0 
	final = [[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0]]
	for i in xrange(1, len(arr)):
		val = arr[i]
		#print val
		time_count =0
		avg=0
		count =0
		flag =0
		
		while(time_count < 25):
			with open('trafficOutput.csv', 'rb') as f:
				sreader = csv.reader(f)
				
				for row in sreader:
					
					tweetID = row[1]
					time_hr = row[0].split(" ")[3].split(":")[0]
					if(val.strip() == tweetID.strip() and (int(time_hr) == time_count or int(time_hr) == time_count + 1 )):
						if(not flag):
							store = row[0].split(" ")[3].split(":")[0]
							flag =1
						avg += float(row[5])
						count +=1
						

			time_count +=2
		if(count >0):
			store = int(store)/2
			final[store][0] += avg
			final[store][1] += count 
			#print place_name, val, store ,avg, count
	for i in xrange(0,12):
		if(final[i][1] != 0):
			final[i][0] /= final[i][1]
	print place_name ,'\t', final