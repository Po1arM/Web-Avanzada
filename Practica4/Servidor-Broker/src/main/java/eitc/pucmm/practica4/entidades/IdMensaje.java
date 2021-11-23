package eitc.pucmm.practica4.entidades;

import java.io.Serializable;

public class IdMensaje implements Serializable {
    private String date;
    private int idCliente;

    public IdMensaje(String date, int idCliente) {
        this.date = date;
        this.idCliente = idCliente;
    }
}
