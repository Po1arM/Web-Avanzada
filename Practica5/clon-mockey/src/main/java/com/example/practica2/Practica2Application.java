package com.example.practica2;

import com.example.practica2.servicios.seguridad.SeguridadServices;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableHazelcastHttpSession
public class Practica2Application {

    public static void main(String[] args) {

        SpringApplication.run(Practica2Application.class, args);

        ApplicationContext applicationContext = SpringApplication.run(Practica2Application.class, args);
        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.crearUsuarioAdmin();
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {//Configuración basica.
        return Hazelcast.newHazelcastInstance();
    }

}
