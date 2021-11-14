package com.example.practica2;

import com.example.practica2.servicios.seguridad.SeguridadServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Practica2Application {

    public static void main(String[] args) {

        //SpringApplication.run(Practica2Application.class, args);

        ApplicationContext applicationContext = SpringApplication.run(Practica2Application.class, args);
        SeguridadServices seguridadServices = (SeguridadServices) applicationContext.getBean("seguridadServices");
        seguridadServices.crearUsuarioAdmin();
    }

}
