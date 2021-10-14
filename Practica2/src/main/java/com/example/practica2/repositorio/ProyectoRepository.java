package com.example.practica2.repositorio;

import com.example.practica2.entidades.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findAllByIdUsuario(long id);
}
