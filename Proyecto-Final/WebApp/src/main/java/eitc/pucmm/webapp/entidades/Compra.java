package eitc.pucmm.webapp.entidades;

import java.util.Date;
import java.util.List;

public class Compra {
    
    private List<Evento> eventos;
    private long idUsuario;
    private long idCompra;
    private Date fecha;

    public Compra() {
    }


    public List<Evento> getEventos() {
        return this.eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdCompra() {
        return this.idCompra;
    }

    public void setIdCompra(long idEmpleado) {
        this.idCompra = idEmpleado;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
