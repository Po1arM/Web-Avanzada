package com.example.practica2.repositorio.seguridad;

import com.example.practica2.entidades.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsername(String username);
}
