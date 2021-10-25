package com.example.practica2.servicios;

import com.example.practica2.entidades.Proyecto;
import com.example.practica2.repositorio.seguridad.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProyectoServices {

    @Autowired
    private static ProyectoRepository proyectoRepository;

    @Transactional
    public Proyecto crearProyecto(Proyecto proyecto){
        return proyectoRepository.save(proyecto);
    }

    public static List<Proyecto> buscarProyectosPorUsername(String username) {
        return proyectoRepository.findAllByUsername(username);

    }
}
