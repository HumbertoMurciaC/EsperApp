package com.example.humbertomariom.login;

/**
 * Created by Humberto Mario M on 23/05/2016.
 */
public class Usuario {

    protected String contrasena;
    protected String correoId;
    protected String nombre;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoId() {
        return correoId;
    }

    public void setCorreoId(String correoId) {
        this.correoId = correoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}