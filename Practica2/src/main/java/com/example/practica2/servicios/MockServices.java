package com.example.practica2.servicios;

import org.springframework.stereotype.Service;
import java.util.Date;
import com.example.practica2.entidades.Mock;
import com.example.practica2.repositorio.MockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MockServices {

    @Autowired
    private static MockRepository mockRepository;

    public static List<Mock> buscarMocksPorProyectoId(long proyectoID) {
        return mockRepository.findAllByIdProyecto(proyectoID);
    }


    public static Date calcularFecha(String fecha){
        int tiempo = Integer.parseInt(fecha);
        long milisec = System.currentTimeMillis() + (tiempo*3600000);
        Date fechaExpiracion = new Date(milisec);
        return fechaExpiracion;
    }

    @Transactional
    public Mock crear(Mock mock){
        mockRepository.save(mock);
        return mock;
    }

    @Transactional
    public void eliminar(long id){mockRepository.deleteById(id);}

    public Mock buscarPorId(long parseLong) {return mockRepository.findById(parseLong).orElse(null);}

    public Mock editar(Mock mock){ //incomplete
        System.out.println("do something");
        return new Mock();
    }
    public Mock buscarMockPorID(long parseLong) {
        return mockRepository.findById(parseLong).orElse(null);
    }

    public void actualizarMock(Mock aux) {
        mockRepository.save(aux);
    }
}

