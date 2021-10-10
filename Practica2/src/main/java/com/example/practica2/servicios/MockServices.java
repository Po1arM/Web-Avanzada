package com.example.practica2.servicios;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MockServices {

    public static Date calcularFecha(String fecha){
        int tiempo = Integer.parseInt(fecha);
        long milisec = System.currentTimeMillis() + (tiempo*3600000);
        Date fechaExpiracion = new Date(milisec);
        return fechaExpiracion;
    }
}
