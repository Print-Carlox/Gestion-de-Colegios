package com.miapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Calificaciones implements Serializable {
    private String asignatura;
    private ArrayList<Double> notasPrimerSemestre;
    private ArrayList<Double> notasSegundoSemestre;
    private double notaFinalSemestral1;
    private double notaFinalSemestral2;

    public Calificaciones(String asignatura) {
        this.asignatura = asignatura;
        this.notasPrimerSemestre = new ArrayList<>();
        this.notasSegundoSemestre = new ArrayList<>();
        this.notaFinalSemestral1 = 0.0;
        this.notaFinalSemestral2 = 0.0;
    }

    public void RegistrarCalificacion(double nota, int semestre) {
        if (nota >= 1.0 && nota <= 7.0) {
            if (semestre == 1) {
                notasPrimerSemestre.add(nota);
            } else if (semestre == 2) {
                notasSegundoSemestre.add(nota);
            }
        }
    }

    public void modificarCalificacion(int index, double nuevaNota, int semestre) {
        if (nuevaNota >= 1.0 && nuevaNota <= 7.0) {
            if (semestre == 1 && index >= 0 && index < notasPrimerSemestre.size()) {
                notasPrimerSemestre.set(index, nuevaNota);
            } else if (semestre == 2 && index >= 0 && index < notasSegundoSemestre.size()) {
                notasSegundoSemestre.set(index, nuevaNota);
            }
        }
    }

    public double calcularPromedioSemestral(int semestre) {
        ArrayList<Double> notas = (semestre == 1) ? notasPrimerSemestre : notasSegundoSemestre;
        if (notas.isEmpty()) return 0.0;
        
        double suma = 0.0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    public void RegistrarCalificacionSemestral(double nota, int semestre) {
        if (nota >= 1.0 && nota <= 7.0) {
            if (semestre == 1) {
                this.notaFinalSemestral1 = nota;
            } else if (semestre == 2) {
                this.notaFinalSemestral2 = nota;
            }
        }
    }

    public double calcularPromedioAnual() {
        if (notaFinalSemestral1 == 0.0) {
            notaFinalSemestral1 = calcularPromedioSemestral(1);
        }
        if (notaFinalSemestral2 == 0.0) {
            notaFinalSemestral2 = calcularPromedioSemestral(2);
        }
        
        if (notaFinalSemestral1 == 0.0 && notaFinalSemestral2 == 0.0) {
            return 0.0;
        }
        if (notaFinalSemestral1 > 0.0 && notaFinalSemestral2 == 0.0) {
            return notaFinalSemestral1;
        }
        return (notaFinalSemestral1 + notaFinalSemestral2) / 2.0;
    }

    public String getAsignatura() { return asignatura; }
    public void setAsignatura(String asignatura) { this.asignatura = asignatura; }
    public ArrayList<Double> getNotasPrimerSemestre() { return notasPrimerSemestre; }
    public ArrayList<Double> getNotasSegundoSemestre() { return notasSegundoSemestre; }
    public double getNotaFinalSemestral1() { return notaFinalSemestral1; }
    public double getNotaFinalSemestral2() { return notaFinalSemestral2; }
}