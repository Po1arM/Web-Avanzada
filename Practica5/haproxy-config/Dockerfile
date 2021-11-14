#Imagen oficial
FROM haproxy:2.3
#Mover el script dentro del contenedor
ADD install.sh /
#Ejecutar el script para instalar certbot y conseguir los certificados ssl
RUN /install.sh
#Mover el archivo de config al contenedor
COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg