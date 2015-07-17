#Case 4: Where both source and destinaiton is 300m about the maneuver points.


import MySQLdb
from datetime import datetime
import timeit
import sys

db = MySQLdb.connect(host="127.0.0.1",
                     port=3306,# your host, usually localhost
                     user="root", # your username
                      passwd="admin", # your password
                      db="traffickramasenti")
cur = db.cursor() 

# Use all the SQL you like
cur.execute("SELECT * FROM traffictweets where location='mumbai';")
dl=[]
# print all the first cell of all the rows
places_list = ['Mrg', 'Marg', 'Rd', 'Road', 'Bridge', 'expressway', 'highway', 'flyover']

def find_between( s, first, last , elem):
    try:
        start = s.index( first ) + len( first )
        end = s.index(last,start)
        return s[start:end] + " " + elem
    except ValueError:
        return ""


for row in cur.fetchall() :
    dle=[]
    dle.append(row[2])
    dl.append(dle)
count =0
b = []
for i in dl:
	for j in places_list:
		if j.lower() in i[0].lower():
			#print i[0].lower()
			#print "</location>" + j.lower()
			a = find_between(i[0].lower(), "<location>", "</location> " + j.lower(), j.lower())
			a = a.replace("<location>", "")
			a = a.replace("</location>", "")
			
			if(a != "" and a not in b):
				print a
				b.append(a)
				count +=1
			break
			
print count 

