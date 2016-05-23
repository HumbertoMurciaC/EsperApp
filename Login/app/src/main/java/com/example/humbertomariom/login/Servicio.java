package com.example.humbertomariom.login;

/**
 * Created by Humberto Mario M on 23/05/2016.
 */
public class Servicio {

    private String idServicio;
    private Sede sede;
    private String tipo;

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
