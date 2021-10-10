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

    @Enumerated(EnumType.STRING)
    private EnumMetodo metodo; //get, post...
    //private Map<String, String> headers = new HashMap<String, String>();
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
    public Mock(long proyectoID, String ruta, String metodo, String headers, int codigo, String conType, String cuerpo, String descripcion, String nombre, Date expiracion, int tRespuesta, boolean jwt) {
    }

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

    public EnumMetodo getMetodo() {
        return metodo;
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

}
