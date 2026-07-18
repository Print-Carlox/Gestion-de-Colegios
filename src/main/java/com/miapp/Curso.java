package com.miapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
    private String codigoCurso;
    private String nombreNivel;
    private Profesor profesorGefe;
    private int cantidadMaximaEstudiantes;
    private ArrayList<Estudiante> estudiantes;

    public Curso(String codigoCurso, String nombreNivel) {
        this.codigoCurso = codigoCurso;
        this.nombreNivel = nombreNivel;
        this.cantidadMaximaEstudiantes = 30;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiantes.size() < cantidadMaximaEstudiantes) {
            estudiantes.add(estudiante);
        } else {
            System.out.println("No se puede agregar más estudiantes. Se ha alcanzado la capacidad máxima del curso.");
        }
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        estudiantes.remove(estudiante);
    }

    public void agregarProfesorJefe(Profesor profesor) {
        this.profesorGefe = profesor;
        if (profesor != null) {
            profesor.asignarCurso(this);
        }
    }

    public double calcularPromedioCurso() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }
        double promedioCurso = 0.0;
        for (Estudiante estudiante: estudiantes) {
            promedioCurso += estudiante.calcularPromedioAnualTotal();
        }
        return promedioCurso / estudiantes.size();
    }

    public double mejorPromedioEstudiante() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes en el curso.");
            return 0.0;
        }
        Estudiante mejorEstudiante = estudiantes.get(0);
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.calcularPromedioAnualTotal() > mejorEstudiante.calcularPromedioAnualTotal()) {
                mejorEstudiante = estudiante;
            }
        }
        return mejorEstudiante.calcularPromedioAnualTotal();
    }

    public void mostrarAsistesnciaEstudiantes() {
        System.out.println("===== ASISTENCIA DEL CURSO " + codigoCurso + " =====");

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados en este curso.");
            return;
        }

        for (Estudiante estudiante : estudiantes) {
            System.out.println("Estudiante: " + estudiante.getNombreCompleto() + ", Porcentaje de asistencia: " + estudiante.getporcentajeAsistencia() + "%");
        }
    }

    public double porcentajeAprobadosCurso() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }

        double aprobados = 0.0;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.aprobo()) {
                aprobados += 1;
            }
        }
        return (aprobados / estudiantes.size()) * 100;
    }

    public double porcentajeAsistenciaCurso() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }

        double porcentaje = 0.0;
        for (Estudiante estudiante : estudiantes) {
            porcentaje += estudiante.getporcentajeAsistencia();
        }
        return porcentaje / estudiantes.size();
    }

    public void mostrarInformacionCurso() {
        System.out.println("===== INFORMACIÓN DEL CURSO =====");
        System.out.println("Código del curso: " + codigoCurso);
        System.out.println("Nombre del nivel: " + nombreNivel);
        if (profesorGefe != null) {
            System.out.println("Profesor jefe: " + profesorGefe.getNombreCompleto());
        } else {
            System.out.println("No hay profesor jefe asignado.");
        }
        System.out.println("Cantidad máxima de estudiantes: " + cantidadMaximaEstudiantes);
        System.out.println("Cantidad de estudiantes inscritos: " + estudiantes.size());
        System.out.println("Promedio del curso: " + calcularPromedioCurso());
        System.out.println("Mejor promedio del curso: " + mejorPromedioEstudiante());
    }




    public String getCodigoCurso() { return codigoCurso; }
    public void setCodigoCurso(String codigoCurso) { this.codigoCurso = codigoCurso; }
    public String getNombreNivel() { return nombreNivel; }
    public void setNombreNivel(String nombreNivel) { this.nombreNivel = nombreNivel; }
    public Profesor getProfesorGefe() { return profesorGefe; }
    public void setProfesorGefe(Profesor profesorGefe) { this.profesorGefe = profesorGefe; }
    public int getCantidadMaximaEstudiantes() { return cantidadMaximaEstudiantes; }
    public void setCantidadMaximaEstudiantes(int cantidadMaximaEstudiantes) { this.cantidadMaximaEstudiantes = cantidadMaximaEstudiantes; }
    public ArrayList<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(ArrayList<Estudiante> estudiantes) { this.estudiantes = estudiantes; }

}
