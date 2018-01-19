from __future__ import print_function
import requests
import mysql.connector
from mysql.connector import errorcode
import time  
import datetime
#time.strftime('%Y-%m-%d %H:%M:%S')


try:
	cnx = mysql.connector.connect(user='root', password='farah',
                              host='127.0.0.1',
                              database='test1')
	
	cursor = cnx.cursor(buffered=True)

except mysql.connector.Error as err:
  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
    print("Something is wrong with your user name or password")
  elif err.errno == errorcode.ER_BAD_DB_ERROR:
    print("Database does not exist")
  else:
    print(err)

                              
DB_NAME = 'ClasseSERI'

TABLES = {}

#TABLES['ClasseSERI'] = (
#   "CREATE TABLE `ClasseSERI` ("
#  "  `emp_no` int(11) NOT NULL AUTO_INCREMENT,"
# "  `birth_date` date NOT NULL,"
#"  `first_name` varchar(14) NOT NULL,"
#"  `last_name` varchar(16) NOT NULL,"
#"  `gender` enum('M','F') NOT NULL,"
#"  `hire_date` date NOT NULL,"
#"  PRIMARY KEY (`emp_no`)"
#") ENGINE=InnoDB")

#definition table de la BDD

TABLES['Liste_EtudiantOK'] = (
    "CREATE TABLE `Liste_EtudiantOK` ("
    "  `identite` varchar(50) NOT NULL,"
    #"  `nom` varchar(50) NOT NULL,"
    "  `adresse` varchar(50) NOT NULL,"
    "  PRIMARY KEY (`identite`)"
    ") ENGINE=InnoDB")
    
TABLES['presence3'] = (
    "CREATE TABLE `presence3` ("
    "  `id` int(11) NOT NULL AUTO_INCREMENT,"
    "  `identite` varchar(50) NOT NULL ,"
    "  `date_photo` date NOT NULL,"
    "  `heure_photo` time NOT NULL,"
    "  `adresse_derniere_photo` varchar(100) NOT NULL,"
    "  `approbation` varchar(5) NOT NULL,"
    "  PRIMARY KEY (`id`) "
    ") ENGINE=InnoDB")
    
    
    
    

def create_database(cursor):
    try:
        cursor.execute(
            "CREATE DATABASE {} DEFAULT CHARACTER SET 'utf8'".format(DB_NAME))
    except mysql.connector.Error as err:
        print("Failed creating database: {}".format(err))
        exit(1)

try:
    cnx.database = DB_NAME  
except mysql.connector.Error as err:
    if err.errno == errorcode.ER_BAD_DB_ERROR:
        create_database(cursor)
        cnx.database = DB_NAME
    else:
        print(err)
        exit(1)


for name, ddl in TABLES.iteritems():
    try:
        print("Creating table {}: ".format(name), end='')
        cursor.execute(ddl)
    except mysql.connector.Error as err:
        if err.errno == errorcode.ER_TABLE_EXISTS_ERROR:
            print("already exists.")
        else:
            print(err.msg)
    else:
        print("OK")
        
#subjects = {"1":"Tristan", "2":"Farah", "3":"Remi","4":"Qianwen","5":"Yanchi", "6":"Mateo", "7":"Sylvain", "8": "Adrien", "9":"Ghislain", "10": "Lin", "11":"Nicolas", "12": "Pierre","13":"Tristan Martin","14":"Mateo Lunette","15":"Manu","16":"Alexandre"}
add_etudiant = 'INSERT INTO Liste_EtudiantOK (identite, adresse) VALUES (%s, %s)'

data_etudiant1 = ('Tristan Duhesme', '/home/pi/Raspberry-Projects/training-data/s1')
data_etudiant2 = ('Farah Mouri', '/home/pi/Raspberry-Projects/training-data/s2')
data_etudiant3 = ('Remi Peltier', '/home/pi/Raspberry-Projects/training-data/s3')
data_etudiant4 = ('Qianwen Bian', '/home/pi/Raspberry-Projects/training-data/s4')
data_etudiant5 = ('Yanchi Lu', '/home/pi/Raspberry-Projects/training-data/s5')
data_etudiant6 = ('Mateo Vallouis', '/home/pi/Raspberry-Projects/training-data/s6')
data_etudiant7 = ('Sylvain Pascou', '/home/pi/Raspberry-Projects/training-data/s7')
data_etudiant8 = ('Adrien Chao', '/home/pi/Raspberry-Projects/training-data/s8')
data_etudiant9 = ('Ghislain Fournier', '/home/pi/Raspberry-Projects/training-data/s9')
data_etudiant10 = ('Chengxian Lin', '/home/pi/Raspberry-Projects/training-data/s10')
data_etudiant11 = ('Nicolas Turcant', '/home/pi/Raspberry-Projects/training-data/s11')
data_etudiant12 = ('Pierre Chauvet', '/home/pi/Raspberry-Projects/training-data/s12')
data_etudiant13 = ('Tristan Martin', '/home/pi/Raspberry-Projects/training-data/s13')
data_etudiant14 = ('MateoLunettes Vallouis', '/home/pi/Raspberry-Projects/training-data/s14')
data_etudiant15 = ('Emmanuel Dervieux', '/home/pi/Raspberry-Projects/training-data/s15')
data_etudiant16 = ('Alexandre Marmignon', '/home/pi/Raspberry-Projects/training-data/s16')

#cursor.execute(add_etudiant, data_etudiant1)
#cursor.execute(add_etudiant, data_etudiant2)
#cursor.execute(add_etudiant, data_etudiant3)
#cursor.execute(add_etudiant, data_etudiant4)
#cursor.execute(add_etudiant, data_etudiant5)
#cursor.execute(add_etudiant, data_etudiant6)
#cursor.execute(add_etudiant, data_etudiant7)
#cursor.execute(add_etudiant, data_etudiant8)
#cursor.execute(add_etudiant, data_etudiant9)
#cursor.execute(add_etudiant, data_etudiant10)
#cursor.execute(add_etudiant, data_etudiant11)
#cursor.execute(add_etudiant, data_etudiant12)
#cursor.execute(add_etudiant, data_etudiant13)
#cursor.execute(add_etudiant, data_etudiant14)
#cursor.execute(add_etudiant, data_etudiant15)
#cursor.execute(add_etudiant, data_etudiant16)
nom = cursor.lastrowid

cnx.commit()










#Affichage Liste_Etudiants
cursor.execute ("SELECT * FROM Liste_EtudiantOK")

print('\n')
print ("\Prenom	 Nom     Dossier	")
print ("===========================================================================")

for reading in cursor.fetchall():
    print (str(reading[0])+"      "+str(reading[1])+"    "+'\n')
  

print('\n')
print("/////////////////////////////////////////////////////////////////////////////\n")






def ajouterRetardataire(nomIdentite, datePhoto, heurePhoto, adresseDerniere_photo, approbation):

	add_Retardataire = 'INSERT INTO presence3 (identite, date_photo, heure_photo, adresse_derniere_photo, approbation) VALUES (%s, %s, %s, %s, %s)'
	data_Retardataire1 = (nomIdentite, datePhoto, heurePhoto, adresseDerniere_photo, approbation)
	
	cursor.execute(add_Retardataire, data_Retardataire1)
	cnx.commit()
	





#Affichage Presence
print("Retardataires du cours du XXXXXXXX de Xh a Xh:\n") 
cursor.execute ("SELECT * FROM presence3")

print ("\Prenom	 Nom Date_photo Heure_arrivee Adresse_derniere_photo Approbation(O/N)	")
print ("===============================================================================")

for reading in cursor.fetchall():
    print (str(reading[0])+" "+str(reading[1])+" "+ str(reading[2])+" " + str(reading[3])+" "+str(reading[4])+" "+ str(reading[5]))


remi= cursor.execute("SELECT identite FROM Liste_Etudiant WHERE adresse ='/home/pi/Raspberry-Projects/training-data/s1'")

for reading in cursor.fetchall():
    print (str(reading[0]))


#~ #Affichage Presence
#~ print("Retardataires du cours du XXXXXXXX de Xh a Xh:\n") 
#~ cursor.execute ("SELECT * FROM presence3")
#~ 
#~ print ("\Prenom	 Nom Date_photo Heure_arrivee Adresse_derniere_photo Approbation(O/N)	")
#~ print ("===============================================================================")
#~ 
#~ for reading in cursor.fetchall():
    #~ print (str(reading))


#cursor.execute("SELECT adresse FROM Liste_Etudiant")
#dirs2 = cursor.fetchall()
#print("Format retour : {}".format(type(dirs2)))
#print(dirs2)cnx.close()
