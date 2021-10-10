package com.example.practica2.entidades.seguridad;

import com.example.practica2.entidades.Mock;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario implements Serializable {

    @Id
    private String username;
    private String password;
    private boolean activo;
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rol> roles;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
    //@JsonIgnore
    private List<Mock> mocks = new ArrayList<>();

    public Usuario(){}
    public Usuario(String username, String password, boolean activo, String nombre, Set<Rol> roles) {
        this.username = username;
        this.password = password;
        this.activo = activo;
        this.nombre = nombre;
        this.roles = roles;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
