package com.example.practica2.servicios;

import com.example.practica2.entidades.seguridad.Usuario;
import com.example.practica2.repositorio.seguridad.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void borrarUsuario(String id){
        usuarioRepository.deleteById(id);
    }

    public Usuario editarUsuario(Usuario usuario){
        Usuario tmp = usuarioRepository.findById(usuario.getUsername()).orElse(null);
        if (tmp==null){
            return tmp;
        }else{
            tmp.setNombre(usuario.getNombre());
            tmp.setPassword(usuario.getPassword());
            tmp.setRoles(usuario.getRoles());
            tmp.setActivo(usuario.isActivo());
            return usuarioRepository.save(tmp);
        }
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(String id){
        return usuarioRepository.findById(id).orElse(null);
    }
}
