package com.miapp;
import java.io.Serializable;
import java.util.ArrayList;

public class Colegio implements Serializable {
    private String codigoEstablecimiento;
    private String nombre;
    private String direccion;
    private String telefono;
    private Director director;
    private ArrayList<Curso> cursos;

    public Colegio(String codigoEstablecimiento, String nombre, String direccion, String telefono) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.nombre = nombre; 
        this.direccion = direccion;
        this.telefono = telefono;
        this.cursos = new ArrayList<Curso>();
    }

    public void asignarDirector(Director director) {
        this.director = director;
    }

    public void agregarCursos(Curso Curso) { 
        cursos.add(Curso);
    }

    public double promedioInstitucional() {
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados");
            return 0.0;
        }
        double sumaPromedios = 0.0;
        for (Curso curso : cursos) {
            sumaPromedios += curso.calcularPromedioCurso();
        }
        return sumaPromedios / cursos.size();
    }

    public double porcentajeAprobacionColegio() {
        if (cursos.isEmpty()) {
            return 0.0;
        }

        double porcentaje = 0.0;
        for (Curso curso : cursos) {
            porcentaje += curso.porcentajeAprobadosCurso();
        }
        return porcentaje / cursos.size();
    }

    public double porcentajeAsistenciaColegio() {
        if (cursos.isEmpty()) {
            return 0.0;
        }

        double porcentaje = 0.0;
        for (Curso curso : cursos) {
            porcentaje += curso.porcentajeAsistenciaCurso();
        }
        return porcentaje / cursos.size();
    }

    public void mostrarInformacionColegio() {
        System.out.println("===== INFORMACIÓN DEL COLEGIO =====");
        System.out.println("Código: " + codigoEstablecimiento);
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono: " + telefono);
        if (director != null) {
            System.out.println("Director: " + director.getNombreCompleto());
        } else {
            System.out.println("Director: No asignado");
        }
        System.out.println("Cursos registrados: " + cursos.size());
    }
    
    public String getCodigoEstablecimiento() { return codigoEstablecimiento; }
    public void setCodigoEstablecimiento(String codigoEstablecimiento) { this.codigoEstablecimiento = codigoEstablecimiento; }
     public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Director getDirector() { return director; }
    public void setDirector(Director director) { this.director = director; }
    public ArrayList<Curso> getCursos() { return cursos; }
    public void setCursos(ArrayList<Curso> cursos) { this.cursos = cursos; }
}
