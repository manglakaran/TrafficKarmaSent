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

# Use all the SQL you like
cur.execute("SELECT d.rideid, d.sourcell, d.destinationll, d.destination, d.source, u.sex, u.genderpreference FROM ridedata d, userdata u where d.userid=u.userid and rideid <> "+sys.argv[1])
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
#print s
check=0
points=(lls[0],lls[1]);
directions_result = gmaps.directions((float(lls[0]),float(lls[1])),
                                 (float(lld[0]),float(lld[1])),
                                 mode="driving",alternatives=True)
pref=sys.argv[5]
sex=sys.argv[4]
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
        mvpoints.append(points)
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
    di=dl[din]; trch=(pref=='Yes' and di[6]=='Yes' and sex==di[5]) or (pref=='No' and di[6]=='No') or (((pref=='Yes' and di[6]=='No')or(pref=='No' and di[6]=='Yes')) and sex==di[5]);
	
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
                    if dists<=0.15 or (dists<=0.5 and destuin==0):
                        ck=ck+1
                elif ck==1:
                    distd=(great_circle(mvp[destuin], destm).meters)/1000
                    if distd<=0.15 or (distd<=0.5 and destuin==len(mvp)-1):
                        ck=ck+1
                        break
            if ck==2:
                #print"User ",din, " matches who is going to ", di[2]
                check=1
                #print di[4], "Matches!!"
                a[destr]['users'][di[4]]=dists+distd


if check==0:
    print "No one matches"
else:
    print len(a)
    for i in range(len(a)):
        print i
        
        
        disp=a[i]['users']
        print len(disp)
        #print"For route ",i
        for key, value in disp.iteritems() :
            print key
            print value
##            
##print '\n'
##print '\n'
##print timeit.default_timer() - start_time
