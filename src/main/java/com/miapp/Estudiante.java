package com.miapp;

import java.util.ArrayList;

public class Estudiante extends Persona {
    private String fechaNacimiento;
    private Curso curso;
    private ArrayList<Calificaciones> registrarNotas;
    private ArrayList<Asistencia> asistencias;
    private double porcentajeAsistencia;

    public Estudiante(String rut, String nombreCompleto, String fechaNacimiento, Curso curso) {
        super(rut, nombreCompleto);
        this.fechaNacimiento = fechaNacimiento;
        this.registrarNotas = new ArrayList<Calificaciones>();
        this.asistencias = new ArrayList<Asistencia>();
        this.porcentajeAsistencia = 0.0;
        this.curso = curso;
    }
    
    public void generarReporte() {
        System.out.println("===== REPORTE DEL ESTUDIANTE =====");
        System.out.println("Nombre: " + this.nombreCompleto);
        System.out.println("RUT: " + this.rut);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Curso: " + (curso != null ? curso.getCodigoCurso() + " - " + curso.getNombreNivel() : "Sin curso asignado"));
        System.out.println("Porcentaje de asistencia: " + getporcentajeAsistencia() + "%");
        System.out.println("-----------------------------------");
        System.out.println("Calificaciones:");

        if (registrarNotas.isEmpty()) {
            System.out.println("No hay calificaciones registradas.");
        } else {
            for (Calificaciones calificacion : registrarNotas) {
                System.out.println("- " + calificacion.getAsignatura());
                System.out.println("  Primer semestre: " + calificacion.getNotasPrimerSemestre());
                System.out.println("  Segundo semestre: " + calificacion.getNotasSegundoSemestre());
            }
        }
    }

    public void mostrarInformacion() {
        System.out.println("===== INFORMACIÓN DEL ESTUDIANTE =====");
        System.out.println("RUT: " + this.rut);
        System.out.println("Nombre completo: " + this.nombreCompleto);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Curso: " + (curso != null ? curso.getCodigoCurso() + " - " + curso.getNombreNivel() : "Sin curso asignado"));
        System.out.println("Porcentaje de asistencia: " + getporcentajeAsistencia() + "%");

        if (registrarNotas.isEmpty()) {
            System.out.println("Calificaciones: No hay calificaciones registradas.");
        } else {
            System.out.println("Calificaciones registradas: " + registrarNotas.size());
        }
    }

    public void agregarCalificaciones(Calificaciones calificacion) {
        registrarNotas.add(calificacion);
    }

    public void agregarAsistencia(Asistencia asistencia) {
        asistencias.add(asistencia);
    }

    public double getporcentajeAsistencia() {
        if (asistencias.isEmpty()) {
            return porcentajeAsistencia;
        }

        int presentes = 0;
        for (Asistencia asistencia : asistencias) {
            if (asistencia.isPresente()) {
                presentes++;
            }
        }

        porcentajeAsistencia = (presentes * 100.0) / asistencias.size();
        return porcentajeAsistencia;
    }

    public double calcularPromedioSemestralTotal(int semestre) {
        if (registrarNotas.isEmpty()) {
            return 0.0;
        }
        double sumaPromedios = 0.0;
        for (Calificaciones calificacion : registrarNotas) {
            sumaPromedios += calificacion.calcularPromedioSemestral(semestre);
        }
        return sumaPromedios / registrarNotas.size();
    }

    public double calcularPromedioAnualTotal() {
        if (registrarNotas.isEmpty()) {
            return 0.0;
        }
        double sumaPromedios = 0.0;
        for (Calificaciones calificacion : registrarNotas) {
            sumaPromedios += calificacion.calcularPromedioAnual();
        }
        return sumaPromedios / registrarNotas.size();
    }

    public boolean aprobo() {
        int reprobadas = 0;
        for (Calificaciones calificacion : registrarNotas) {
            if (calificacion.calcularPromedioAnual() <= 4) {
                reprobadas += 1;
            }
        }
        if (reprobadas >= 3) {
            return false;
        }
        return true;

    }


    public String getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(String fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
    public Curso getCurso() {return curso;}
    public void setCurso(Curso curso) {this.curso = curso;}
    public ArrayList<Calificaciones> getRegistrarNotas() {return registrarNotas;}
    public void setRegistrarNotas(ArrayList<Calificaciones> registrarNotas) {this.registrarNotas = registrarNotas;}
}
