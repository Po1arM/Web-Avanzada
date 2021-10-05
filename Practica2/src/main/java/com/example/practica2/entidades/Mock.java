package com.example.practica2.entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Mock implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruta;
    private String metodo; //considerar ENUM //get, post...
    private String headers; //usar map
    private int codigo; // 200, 404...
    private String contype; //otro droplist
    private String cuerpo;
    private String descripcion; // nombre y descripcion del endpoint
    private String expiracion; // 1 mes, semana, dia u hora. ENUM??
    private int tiempoRespuesta; //en segundos, default 0
    private Boolean jwt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
