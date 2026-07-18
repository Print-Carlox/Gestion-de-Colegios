package com.miapp;
import java.io.Serializable;
import java.util.ArrayList;

public class DireccionComunal implements Serializable {
    private String nombreDireccionComunal;
    private ArrayList<Colegio> colegios;
    private ArrayList<Profesor> profesores;

    public DireccionComunal(String nombreDireccionComunal) {
        this.nombreDireccionComunal = nombreDireccionComunal;
        this.colegios = new ArrayList<Colegio>();
        this.profesores = new ArrayList<Profesor>();
    }

    public void agregarColegio(Colegio colegio) {
        if (colegio != null && !colegios.contains(colegio)) {
            this.colegios.add(colegio);
        }
    }

    public void agregarProfesor(Profesor profesor) {
        if (profesor != null && !profesores.contains(profesor)) {
            profesores.add(profesor);
        }
    }

    public double porcentajePromedioComunal() {
        if (colegios.isEmpty()) {
            return 0.0;
        }

        double porcentaje = 0.0;
        for (Colegio colegio : colegios) {
            porcentaje += colegio.porcentajeAprobacionColegio();
        }
        return porcentaje / colegios.size();
    }

    public double porcentajeAsistenciaComunal() {
        if (colegios.isEmpty()) {
            return 0.0;
        }

        double porcentaje = 0.0;
        for (Colegio colegio : colegios) {
            porcentaje += colegio.porcentajeAsistenciaColegio();
        }
        return porcentaje / colegios.size();
    }

    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void mostrarInformacionDireccionComunal() {
        System.out.println("===== DIRECCIÓN COMUNAL =====");
        System.out.println("Nombre: " + nombreDireccionComunal);
        System.out.println("Colegios registrados: " + colegios.size());
    }

    public String getNombreDireccionComunal() { return nombreDireccionComunal; }
    public void setNombreDireccionComunal(String nombreDireccionComunal) { this.nombreDireccionComunal = nombreDireccionComunal; }
    public ArrayList<Colegio> getColegios() { return colegios; }
    public void setColegios(ArrayList<Colegio> colegios) { this.colegios = colegios; }

}

