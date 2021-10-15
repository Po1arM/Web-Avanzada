package com.example.practica2.repositorio.seguridad;

import com.example.practica2.entidades.Mock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockRepository extends JpaRepository<Mock, Long> {

    List<Mock> findAllByIdProyecto(long id);
    Mock findById(long id);
}