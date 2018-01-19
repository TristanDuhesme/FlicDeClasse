import numpy as np
import picamera
import time
import picamera.array
import os

nom = "Alexandre"
n = 30

dossier = "./entrainement/"+nom+"/"
if not os.path.exists(dossier):
    os.makedirs(dossier)

camera = picamera.PiCamera() 
camera.resolution = (640, 480)
camera.start_preview(fullscreen=False, window = (100, 20, 320, 240))
time.sleep(3)

for i in range (n):
    camera.capture(dossier + "im" + str(i) + ".jpg")
    time.sleep(1)
    print(n-i)

print("Merci "+nom+" !")
