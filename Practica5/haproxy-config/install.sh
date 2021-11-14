#Instalar snap
apt update
apt install snapd -y

#Instalar Certbot
snap install --classic certbot
ln -s /snap/bin/certbot /usr/bin/certbot

#Ejecutar certbot para obtener credenciales
sudo certbot certonly --standalone -d <URI>  -m <Correo>

#Las credenciales se guardan en /etc/letsencrypt/live/<URI>