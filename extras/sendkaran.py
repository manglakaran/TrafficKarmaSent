import csv 

counter=101
time =0
speed_temp = 0.0
speed_avg = 0.0
speed_count =0

ofile  = open("historical_data2.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["SER","Distance","Speed","TIME"])
ofile.close()
print "asdasda"
while(counter < 201):
	time =0
	while(time < 24):
		with open('sendkaran2.csv', 'rb') as csvfile:
			reader = csv.DictReader(csvfile)
			for row in reader:
				if(int(row["SER"]) == counter):
					if int(row["TIME"].split(" ")[1].split(":")[0]) == time :
						#print float(row["Speed"])
						speed_temp += float(row["Speed"])
						speed_count +=1
						distance = row["Distance"]
					
		if(speed_count > 0):
			print counter,distance,speed_temp/speed_count,time
		
			ofile  = open("historical_data2.csv", "a+b")
			c = csv.writer(ofile)
			c.writerow([counter,distance,speed_temp/speed_count,time])
			ofile.close()
		speed_temp = 0.0
		speed_count = 0
		time +=1
	counter+=1