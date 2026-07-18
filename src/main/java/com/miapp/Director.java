package com.miapp;

public class Director extends Persona {
    private Colegio colegio;
    private int telefono;
    private String correo;

    public Director(String rut, String nombreCompleto, int telefono, String correo) {
        super(rut, nombreCompleto);
        this.telefono = telefono;
        this.correo = correo;
    }

    public void generarReporte() {
        System.out.println("===== REPORTE DEL DIRECTOR =====");
        System.out.println("Nombre: " + this.nombreCompleto);
        System.out.println("RUT: " + this.rut);
        System.out.println("Teléfono: " + this.telefono);
        System.out.println("Correo: " + this.correo);
        System.out.println("Colegio asignado: " + (this.colegio != null ? this.colegio.getNombre() : "Sin colegio asignado"));
    }

    public void mostrarInformacion() {
        System.out.println("===== INFORMACIÓN DEL DIRECTOR =====");
        System.out.println("RUT: " + this.rut);
        System.out.println("Nombre completo: " + this.nombreCompleto);
        System.out.println("Teléfono: " + this.telefono);
        System.out.println("Correo: " + this.correo);
        System.out.println("Colegio: " + (this.colegio != null ? this.colegio.getNombre() : "Sin colegio asignado"));
    }

    public void asignarColegio(Colegio colegio) { this.colegio = colegio; }
    public Colegio getColegio() { return colegio; }
    public void setColegio(Colegio colegio) { this.colegio = colegio; }
    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
