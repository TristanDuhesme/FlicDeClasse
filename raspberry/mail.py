import smtplib

from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEBase import MIMEBase
from email import encoders


def envoyer_mail(eleve, filename, path_filename):
	#eleve = "Remi"
	fromaddr = "tde195@gmail.com"
	toaddr = "tde195@gmail.com"
	toaddr2 = "peltier.remi2@hotmail.fr"
	toaddr3 = "debarbuat.aymar@gmail.com"
	msg = MIMEMultipart()
	msg['From']  = "Flic de Classe"
	msg['To'] = toaddr
	msg['subject'] = "Nouvelle intrusion dans la salle"
	body = "Un nouvel eleve s'est introduit dans la salle. Il s'agit tres probablement de "+eleve+"."

	msg.attach(MIMEText(body, 'plain'))

	#filename = "test.jpg"
	#path_filename = "./Test/test.jpg"
	attachment = open(path_filename, "rb")

	part = MIMEBase('application', 'octet-stream')
	part.set_payload((attachment).read())
	encoders.encode_base64(part)
	part.add_header('Content-Disposition', "attachment; filename= %s" % filename)
	msg.attach(part)

	server = smtplib.SMTP('smtp.gmail.com', 587)
	server.starttls()
	server.login(fromaddr,"tristan95")
	text = msg.as_string()
	server.sendmail(fromaddr, toaddr, text)
	#server.sendmail(fromaddr, toaddr2, text)
	#server.sendmail(fromaddr, toaddr3, text)
	server.quit
	print("Mail envoyé : "+eleve)
