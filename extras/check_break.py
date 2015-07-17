import csv 

with open('historical_data.csv', 'rb') as csvfile:
	reader = csv.DictReader(csvfile)
	for row in reader:
		if int(row["TIME"]) == 0 :
			save = float(row["Speed"])
		else:
			if(float(row["Speed"]) - save >= 0.1*save or -float(row["Speed"]) + save >= 0.1*save ):
				print row["SER"] + "->" , int(row["TIME"])-1
			save = float(row["Speed"])
		
			