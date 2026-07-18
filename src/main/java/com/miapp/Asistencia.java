package com.miapp;

import java.io.Serializable;

public class Asistencia implements Serializable {
    private String fecha;
    private boolean presente;
    private String asignatura;
    private boolean justificada;

    public Asistencia(String fecha, boolean presente, String asignatura, boolean justificada) {
        this.fecha = fecha;
        this.presente = presente;
        this.asignatura = asignatura;
        this.justificada = justificada;
    }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }
    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }
    public boolean isJustificada() { return justificada; }
    public void setJustificada(boolean justificada) { this.justificada = justificada; }
}
