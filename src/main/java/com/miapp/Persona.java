package com.miapp;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    protected String rut;
    protected String nombreCompleto;

    public Persona(String rut, String nombreCompleto) {
        this.rut = rut;
        this.nombreCompleto = nombreCompleto;
    }

    public abstract void mostrarInformacion();
    public abstract void generarReporte();

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}
