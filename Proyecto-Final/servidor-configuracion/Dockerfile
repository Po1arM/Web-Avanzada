FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:11.0.12-jre-slim-buster
# indicando el puerto para exponer, debo pasar el flag -p para habilitarlo o -P para publicarlos todos.
EXPOSE 8888

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/servidor-configuracion.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/servidor-configuracion.jar"]