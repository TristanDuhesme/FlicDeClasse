from __future__ import print_function
import requests
import mysql.connector
from mysql.connector import errorcode
import time  
import datetime


from string import maketrans   # Required to call maketrans function.


cnx = mysql.connector.connect(user='root', password='farah',
                              host='127.0.0.1',
                              database='ClasseSERI')
	
cursor = cnx.cursor(buffered=True)



intab = "/home/pi/Raspberry-Projects/training-data/s"
outtab = "                                           "
trantab = maketrans(intab, outtab)

str = "/home/pi/Raspberry-Projects/training-data/s8";
modif=(str.translate(trantab))
print (modif)
print(modif.strip())

