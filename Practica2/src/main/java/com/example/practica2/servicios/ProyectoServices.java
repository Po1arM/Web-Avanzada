package com.example.practica2.servicios;

import com.example.practica2.entidades.Proyecto;
import com.example.practica2.repositorio.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServices {

    @Autowired
    private static ProyectoRepository proyectoRepository;

    public static List<Proyecto> buscarProyectosPorUserId(long id) {
        return proyectoRepository.findAllByIdUsuario(id);

    }
}
