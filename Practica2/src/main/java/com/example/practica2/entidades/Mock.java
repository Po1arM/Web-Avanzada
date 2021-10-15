package com.example.practica2.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Mock implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long idProyecto;
    private String ruta;

    /*
    //private Map<String, String> headers = new HashMap<String, String>();
    */
    @Enumerated(EnumType.STRING)
    private EnumMetodo metodo; //get, post...
    private String headers; //usar map
    private int codigo; // 200, 404...
    private String contype; //otro droplist
    private String cuerpo;
    private String descripcion; // nombre y descripcion del endpoint
    private String nombre;
    private Date expiracion;
    private int tiempoRespuesta; //en segundos, default 0
    private Boolean jwt;

    public Mock(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setMetodo(EnumMetodo metodo) {
        this.metodo = metodo;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setContype(String contype) {
        this.contype = contype;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

    public void setTiempoRespuesta(int tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public void setJwt(Boolean jwt) {
        this.jwt = jwt;
    }

    public long getIdProyecto() {
        return idProyecto;
    }

    public String getRuta() {
        return ruta;
    }

    public String getMetodo() {
        return metodo.toString();
    }

    public String getHeaders() {
        return headers;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getContype() {
        return contype;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public Boolean getJwt() {
        return jwt;
    }

    public void setIdProyecto(long idProyecto) {
        this.idProyecto = idProyecto;
    }
}
