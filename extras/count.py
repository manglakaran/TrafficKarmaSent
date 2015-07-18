import csv

ofile  = open("count.csv", "a+b")
c = csv.writer(ofile)
c.writerow(["SER","Distance","Speed","TIME"])
ofile.close()

fo = open("newnodes.txt","r")
for i in fo.readlines():
	print i

	#ofile  = open("count.csv", "a+b")
	#c = csv.writer(ofile)
	#c.writerow([i])
	#ofile.close()
