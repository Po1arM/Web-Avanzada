#Instalar snap
apt update
apt install snapd -y

#Instalar Certbot
snap install --classic certbot
ln -s /snap/bin/certbot /usr/bin/certbot

#Ejecutar certbot para obtener credenciales
sudo certbot certonly --standalone -d practica5.andresestrella.codes  -m andresmauricioestrella@gmail.com

#Las credenciales se guardan en /etc/letsencrypt/live/<URI>
#Unir credenciales dentro de un solo archivo para ser usadas por haproxy
mkdir -p /etc/haproxy/certs
DOMAIN='practica5.andresestrella.codes' 
cat /etc/letsencrypt/live/$DOMAIN/fullchain.pem /etc/letsencrypt/live/$DOMAIN/privkey.pem > /etc/haproxy/certs/$DOMAIN.pem
cat /etc/letsencrypt/live/practica5.andresestrella.codes/fullchain.pem /etc/letsencrypt/live/practica5.andresestrella.codes/privkey.pem > /etc/haproxy/certs/practica5.andresestrella.codes.pem
chmod -R go-rwx /etc/haproxy/certs