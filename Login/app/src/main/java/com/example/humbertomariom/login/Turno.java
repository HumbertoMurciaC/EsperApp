package com.example.humbertomariom.login;

import javax.xml.datatype.XMLGregorianCalendar;

public class Turno {

    private String atendido;
    private String idTurno;
    private String numTurno;
    private Sede sede;
    private Servicio servicioID;
    private Usuario usuario;

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }


    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getNumTurno() {
        return numTurno;
    }

    public void setNumTurno(String numTurno) {
        this.numTurno = numTurno;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Servicio getServicioID() {
        return servicioID;
    }

    public void setServicioID(Servicio servicioID) {
        this.servicioID = servicioID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}