#Case 4: Where both source and destinaiton is 300m about the maneuver points.

from geopy.distance import great_circle
import MySQLdb
import googlemaps
from datetime import datetime
from datetime import timedelta
import timeit
import sys

gmaps = googlemaps.Client(key='AIzaSyBJeg_i4UJ0wt0nnRJ3em3biiq5U9RjiL8')


start_time = timeit.default_timer()


db = MySQLdb.connect(host="127.0.0.1",
					 port=3306,# your host, usually localhost
					 user="root", # your username
					  passwd="admin", # your password
					  db="rideshare")
cur = db.cursor() 

#Source as 28.545996,77.272637
#Destination as 28.612674,77.277262

#28.545996,77.272637 28.612674,77.277262


# cd C:\Users\Server\Documents\NetBeansProjects\MapBusesRoute\src\java\rideshare>python find_distance_output_n.py 150 28.545996,77.272637 28.612674,77.277262 Male No
ts=long(sys.argv[7])
tsl=str(ts-600000)
tsu=str(ts+2200000)
#print tsl
#print tsu

# Use all the SQL you like
cur.execute("SELECT d.rideid, d.sourcell, d.destinationll, d.destination, d.source, d.gender, d.pref FROM ridedata_a d where d.done<>'Yes' and d.timestamp between '"+tsl+"'and '"+tsu+"' and d.userid = '"+sys.argv[1]+"' and d.rideid<>'"+sys.argv[6]+"'")
dl=[]
# print all the first cell of all the rows
for row in cur.fetchall() :
	dle=[]
	dle.append(row[1])
	dle.append(row[2])
	dle.append(row[3])
	dle.append(row[4])
	dle.append(row[0])
	dle.append(row[5])
	dle.append(row[6])
	dl.append(dle)

#print dl


sin="User hello "
##si=dl[sin]
s=sys.argv[2]
#print s
lls=s.split(',')
sourceu=(lls[0],lls[1])
s=sys.argv[3]
lld=s.split(',')
destinationp=(lld[0],lld[1])
#print s
check=0
#points=(lls[0],lls[1]);
distance_going=(great_circle(destinationp, sourceu).meters)/1000
directions_result = gmaps.directions((float(lls[0]),float(lls[1])),
								 (float(lld[0]),float(lld[1])),
								 mode="driving",alternatives=True)

sex=sys.argv[4]
pref=sys.argv[5]

#print directions_result
a={}
t=0
x=0
#print "For user ",sin
#print "No of routes available is: ",len(directions_result)
for i in range(len(directions_result)):
	r= directions_result[i]
	if i==0:
		t=r['legs'][0]['duration']['value']
		b={}
		steps= r['legs'][0]['steps']
		mvpoints=[]
		mvpoints.append(sourceu)
		for estep in steps:
			point=(estep['end_location']['lat'],estep['end_location']['lng'])
			#print point
			mvpoints.append(point)
		b['mvpo']=mvpoints
		b['users']={}
		a[0]=b
		#print a
		x=x+1
		
	else:
		
		if abs(t-(r['legs'][0]['duration']['value']))<600:
			#print "Inside alter"
			b={}        
			steps= r['legs'][0]['steps']
			mvpoints=[]
			for estep in steps:
				point=(estep['end_location']['lat'],estep['end_location']['lng'])
				#print point
				mvpoints.append(point)
			#print mvpoints
			b['mvpo']=mvpoints
			b['users']={}
			a[x]=b
			x=x+1
			
#print dl;
#print a
#print "No of routes added is: ",len(a)

for din in range(len(dl)):
	di=dl[din]; 	trch=(pref=='Yes' and di[6]=='Yes' and sex==di[5]) or (pref=='No' and di[6]=='No') or (((pref=='Yes' and di[6]=='No')or(pref=='No' and di[6]=='Yes')) and sex==di[5]); #print trch;
	
	if (trch):
		anyd=0
		#print di[0]
		d=di[0]
		#print (s);
		#print "Here inside!!!"
		ll=d.split(',')
		sourcem=(ll[0],ll[1])
		d=di[1]
		ll=d.split(',')
		destm=(ll[0],ll[1])
	
		for destr in range(len(a)):
			mvp=a[destr]['mvpo']
			#print "In route", destr
			ck=0
			for destuin in range(len(mvp)):
				if ck==0:
					dists=(great_circle(mvp[destuin], sourcem).meters)/1000
					if dists<=0.3 or (dists<=2 and destuin==0):
						ck=ck+1
				elif ck==1:
					distd=(great_circle(mvp[destuin], destm).meters)/1000
					if distd<=0.3 or (distd<=2 and destuin==len(mvp)-1):
						ck=ck+1
						break
			if ck==2:
				#print"User ",din, " matches who is going to ", di[2]
				
				#print di[4], "Matches!!"
				valueoverlap=((great_circle(sourcem, destm).meters)/1000)/(distance_going)
				if valueoverlap>1:
					valueoverlap=1/valueoverlap
				valueoow=distd+dists
				calcval=valueoverlap*0.85-valueoow*0.15
				if calcval>0.50:
					a[destr]['users'][str(di[4])+","+str(destr)+","+str(valueoverlap)+","+str(valueoow)]=calcval
					check=1


				

if check==0:
	print "No one matches"
else:
	finallist={}
	finallist_c={}
	#print len(a)
	for i in range(len(a)):
		#print i
		
		
		disp=a[i]['users']
		#print len(disp)
		#print"For route ",i
		for key, value in disp.iteritems() :
			
			if (str(key).split(",")[0]) in finallist_c:
				if finallist.get(finallist_c.get(str(key).split(",")[0]))<value:
					del finallist[finallist_c.get(str(key).split(",")[0])]
					finallist[str(key)]=value
					finallist_c[str(key).split(",")[0]]=str(key)
			else:
				finallist[str(key)]=value
				finallist_c[str(key).split(",")[0]]=str(key)
				
			#print key
			#print value
		    
			
	import operator
	#x = {1: 2, 3: 4, 4: 3, 2: 1, 0: 0}
	sorted_list = sorted(finallist.items(), key=operator.itemgetter(1), reverse=True)
	#yn= sorted_list[0]
	#y= yn[0]
	for key, value in sorted_list[0:5]:
		print key
	#cur.execute("UPDATE ridedata set done='Yes' where rideid="+y.split(",")[0])
	#cur.execute("INSERT into matchdata (RequestRid, AskedRid) values ("+sys.argv[6]+","+y.split(",")[0]+")")

#cur.execute("UPDATE ridedata set done='Yes' where rideid="+sys.argv[6]);
db.commit()
db.close()
	
##            
##print '\n'
##print '\n'
##print timeit.default_timer() - start_time
