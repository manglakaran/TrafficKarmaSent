import csv

fo = open("tp.txt","r")
ofile  = open("tp.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["a", "b", "c", "d"])

ofile.close()

for i in fo.readlines():

	a = i.split(",")[0]
	d = i.split(",")[2]
	e = i.split(",")[1]
	b = e.split()[0]
	ce = e.split()[1]
	print a,b,c,d
	ofile  = open("tp.csv", "a+b")
	c = csv.writer(ofile)
	c.writerow([a, b, ce, d])

	ofile.close()

#	print lat_1, lng_1, lat_2,lng_2