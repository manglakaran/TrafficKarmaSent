import csv

ofile  = open("count.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["count"])
ofile.close()


for i in xrange(1,361):
	print i 
	ofile  = open("count.csv", "a+b")
	c = csv.writer(ofile)
	c.writerow([i])
	ofile.close()
