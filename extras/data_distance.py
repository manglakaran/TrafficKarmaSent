from geopy.distance import great_circle
l = []
p= []
fo = open("nodes.txt", "r")
for i in fo.readlines():
	lat_1 , lng_1 = i.split(",")[0] , i.split(",")[1][0:-1]
	fo2 = open("nodes2.txt", "r")
	for i in fo2.readlines():
		lat_2 , lng_2 = i.split(",")[0] , i.split(",")[1][0:-1]

		sourceu=(lat_1, lng_1)
		sourcem=(lat_2,lng_2)
		if((great_circle(sourceu, sourcem).meters) >= 500.0 and (great_circle(sourceu, sourcem).meters) <=750.0):
			#print (great_circle(sourceu, sourcem).meters)
			#p.append((sourceu, sourcem))
			if (sourcem, sourceu) not in l:
				l.append((sourceu, sourcem))
			
			
print l

