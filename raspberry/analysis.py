import numpy as np
import picamera
import time
import picamera.array
import os
#import detectionIdentite as yop
import detectionIdentiteTest as yopTest
import mail as m
import shutil
import testmysql as bdd
import datetime
import mysql.connector


cnx = mysql.connector.connect(user='root', password='farah',
                              host='127.0.0.1',
                              database='ClasseSERI')
cursor = cnx.cursor(buffered=True)


class DetectMotion(picamera.array.PiMotionAnalysis):
    def analyze(self, a):
        a = np.sqrt(
            np.square(a['x'].astype(np.float)) +
            np.square(a['y'].astype(np.float))
            ).clip(0, 255).astype(np.uint8)
        # If there're more than 10 vectors with a magnitude greater
        # than 60, then say we've detected motion
        if (a > 60).sum() > 10:
            fileName = time.strftime("%Y%m%d/%Y%m%d-%H%M%S.jpg",time.localtime())
            camera.capture("./motionDetection/"+fileName)
            filename = time.strftime("%Y%m%d-%H%M%S.jpg",time.localtime())
            print(filename)
            # Traitement de l'image
            imagePredie, eleve = yopTest.identificationImage(face_recognizer, "./motionDetection/"+fileName)
            #yop.afficherImageIdentifie(imagePredie)
            print('Motion detected : ' + str(eleve))
            # Emission mail
            if(eleve != None):
				#listdir = os.listdir('/home/pi/Raspberry-Projects/training-data/s'+str(label))
				#print(listdir)
				#nb = size(listdir)
				#print(nb)
				#shutil.copyfile("./motionDetection/"+fileName, '/home/pi/Raspberry-Projects/training-data/s'+label +'/img')
				m.envoyer_mail(eleve, filename, "./motionDetection/"+fileName)
				bdd.ajouterRetardataire(eleve, datetime.datetime.now().date(), datetime.datetime.now().time(), filename, 'Oui')

##Initialisation (labels, photos)
#face_recognizer = yop.initialiserDonnees('/home/pi/Raspberry-Projects/training-data') 
face_recognizer = yopTest.initialiserDonnees('Liste_EtudiantOK') 

face_recognizer.save('entrainementBDD.xml')

#face_recognizer = yop.load_training_data('entrainement8.xml')
face_recognizer = yopTest.load_training_data('entrainementBDD.xml')

d = time.strftime("motionDetection/%Y%m%d") 
if not os.path.exists(d):
    os.makedirs(d)
with picamera.PiCamera() as camera:
    with DetectMotion(camera) as output:
        camera.resolution = (640, 480)
        camera.start_preview(fullscreen=False, window = (100, 20, 320, 240))
        time.sleep(3)
        camera.start_recording('/dev/null', format='h264', motion_output=output)
        camera.wait_recording(600)
        camera.stop_recording()
        

