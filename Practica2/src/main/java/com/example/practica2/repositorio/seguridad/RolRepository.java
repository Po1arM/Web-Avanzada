package com.example.practica2.repositorio.seguridad;

import com.example.practica2.entidades.seguridad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,String> {

    //Cualquier metodo que necesite incluir.
    
}
