FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:11.0.12-jre-slim-buster

ENV spring.datasource.username='db'
ENV spring.datasource.password='12345678'
ENV DB_NAME=Notificacion_sensores
ENV DB_HOST=base-datos
ENV PUERTO_APP=8080

EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/Servidor-Broker.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/Servidor-Broker.jar"]