FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar

FROM openjdk:11.0.12-jre-slim-buster

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/Cliente.jar
ARG ID
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/praCliente.jar", ${ID}]