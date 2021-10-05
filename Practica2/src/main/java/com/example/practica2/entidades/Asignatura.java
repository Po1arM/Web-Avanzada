package com.example.practica2.entidades;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacax on 24/09/16.
 */
@Entity
@Data
public class Asignatura implements Serializable {

    @Id
    @GeneratedValue
    private
    long id;
    private String clave;
    private String nombre;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio=new Date();

}
