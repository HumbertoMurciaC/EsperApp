package com.example.humbertomariom.login;

import javax.xml.datatype.XMLGregorianCalendar;

public class Turno {

    private String atendido;
    private XMLGregorianCalendar fecha;
    private Sede sede;
    private Usuario usuario;

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }

    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(XMLGregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}