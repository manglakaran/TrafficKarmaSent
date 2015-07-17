import csv
with open('trafficOutput.csv', 'rb') as f:
	sreader = csv.reader(f)
	sreader.next()
	i=1
	prev =42
	for row in sreader:
		new= int(row[4].split(" ")[-1].split(":")[1])
		if(abs(new-prev) >3):
			i+=1
		if(i == 49):
			break
		prev= int(row[4].split(" ")[-1].split(":")[1])	
		tweetID = row[1]
		t = row[4]
		time = row[4].split(" ")[-1].split(":")[0]
		sreader.next()
		with open('trafficOutput2.csv', 'rb') as f2:
			count =0
			sreader2 = csv.reader(f2)
			sreader2.next()
			for row2 in sreader2:
				if(row2[4].split(" ")[-1].split(":")[0] == time and row2[1] == tweetID and (row2[2] != row[3] and row[3] != row2[2]) and \
				row2[2] == row[2] and row2[3] == row[3]):
					#print row2[4]
					#print row[4]
					#print (int(row[4].split(" ")[-1].split(":")[1]) >=30 )and (int(row[4].split(" ")[-1].split(":")[1] ) < 60)
					if(((int(row[4].split(" ")[-1].split(":")[1] )>=30 )and (int(row[4].split(" ")[-1].split(":")[1] )< 60) and \
					(int(row2[4].split(" ")[-1].split(":")[1] )>=30 )and (int(row2[4].split(" ")[-1].split(":")[1] )< 60)) or \
					((int(row[4].split(" ")[-1].split(":")[1] )>=0 )and (int(row[4].split(" ")[-1].split(":")[1] )< 30) and \
					(int(row2[4].split(" ")[-1].split(":")[1] )>=0 )and (int(row2[4].split(" ")[-1].split(":")[1] )< 30))):
						count +=1
						print tweetID, row2[5], row2[4], count, i 
				sreader2.next()		
					