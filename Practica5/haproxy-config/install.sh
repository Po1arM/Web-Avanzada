#Instalar snap
sudo apt update
sudo apt install snapd -y

#Instalar Certbot
sudo snap install --classic certbot
sudo ln -s /snap/bin/certbot /usr/bin/certbot

echo -ne '\n'

#Ejecutar certbot para obtener credenciales
sudo certbot certonly --standalone -d <URI>  -m <Correo>

#Las credenciales se guardan en /etc/letsencrypt/live/<URI>
