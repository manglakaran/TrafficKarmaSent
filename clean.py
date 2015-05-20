# -*- coding: utf-8 -*-
import re,sys


with open ("data.txt", "r") as myfile:
    data1=myfile.read().replace('\n', '')

data = data1.decode('utf_8')
elements = []
dsplit = re.split('[$$$]',data)
udata = [x for x in dsplit if x ]
 

for i in udata:
	pair= i.split("###")
	elements.append(pair[-2])

  
for n,i in enumerate(elements):
		
	elements[n] = re.sub(r"htt\S+", "", elements[n])

for i in elements : 
	print i + "\n"


