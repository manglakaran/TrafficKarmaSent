#Case 4: Where both source and destinaiton is 300m about the maneuver points.


import MySQLdb
from datetime import datetime
import timeit
import sys,re

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
places_list = ['Mrg', 'Marg', 'Rd', 'Road', 'Bridge', 'expressway', 'highway', 'flyover', 'mall', 'east', 'west']

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
show = []
for i in dl:
	found = []
	for m in re.finditer('LOCATION>', i[0]):
		
		found.append((m.start(), m.end()))
	if(len(found) > 2):
		#print found
		to_check = i[0][found[1][1] : found[2][0]-1]
		
		
		if ( 'to' in to_check or 'towards' in to_check or 'from' in to_check or 'leading' in to_check ):
			
			#print i[0]
			#places_list = ['Mrg', 'Marg', 'Rd', 'Road', 'Bridge', 'expressway', 'highway', 'flyover']
			end = ""
			for place in places_list:
				if(place.lower() == to_check.split(" ")[1].lower()):
					end = place
			show.append((i[0][found[0][1] : found[1][0] -2] +" " +end , i[0][found[2][1] : found[3][0] -2]))	
			#print i[0][found[0][1] : found[1][0] -2] +" " +end
			#print i[0][found[2][1] : found[3][0] -2]
			#print to_check.split(" ")[1].lower()
			#print '\n'
			#print i[0] +'\n'
			count +=1
show = list(set(show))

for i in show:
	print i[0] + '\t' + i[1]
print len(show)
	
