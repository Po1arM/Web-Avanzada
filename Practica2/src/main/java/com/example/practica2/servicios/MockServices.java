package com.example.practica2.servicios;

import org.springframework.stereotype.Service;
import java.util.Date;
import com.example.practica2.entidades.Mock;
import com.example.practica2.repositorio.MockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MockServices {

    @Autowired
    private MockRepository mockRepository;

    public long cantidadMocks(){return mockRepository.count();}

    public static Date calcularFecha(String fecha){
        int tiempo = Integer.parseInt(fecha);
        long milisec = System.currentTimeMillis() + (tiempo*3600000);
        Date fechaExpiracion = new Date(milisec);
        return fechaExpiracion;
    }

    @Transactional
    public Mock crearMock(Mock mock){
        mockRepository.save(mock);
        return mock;
    }

    public void eliminarMock (long id){
        mockRepository.deleteById(id);
    }

    public Mock buscarMockById(long parseLong) {
        return mockRepository.findById(parseLong).orElse(null);
    }


}

