package com.miapp;

import java.util.ArrayList;

public class Profesor extends Persona {
    private String correo;
    private String especialidad;
    private String telefono;
    private ArrayList<Curso> cursosAsignados;

    public Profesor(String rut, String nombre, String correo, String especialidad, String telefono) {
        super(rut, nombre);
        this.correo = correo;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.cursosAsignados = new ArrayList<>();
    }

    public void asignarCurso(Curso curso) {
        if (curso != null && !cursosAsignados.contains(curso)) {
            cursosAsignados.add(curso);
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("RUT: " + this.rut);
        System.out.println("Nombre completo: " + this.nombreCompleto);
        System.out.println("Correo: " + this.correo);
        System.out.println("Teléfono: " + this.telefono);
        System.out.println("Especialidad: " + this.especialidad);
    }

    @Override
    public void generarReporte() {
        System.out.println("Reporte del profesor: " + this.nombreCompleto);
        System.out.println("RUT: " + this.rut);
        System.out.println("Correo: " + this.correo);
        System.out.println("Teléfono: " + this.telefono);
        System.out.println("Especialidad: " + this.especialidad);
        System.out.println("Cursos asignados: " + cursosAsignados.size());
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public ArrayList<Curso> getCursosAsignados() { return cursosAsignados; }
    public void setCursosAsignados(ArrayList<Curso> cursosAsignados) { this.cursosAsignados = cursosAsignados; }
}
