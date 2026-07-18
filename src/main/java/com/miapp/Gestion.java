package com.miapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion {
    private static final Scanner entrada = new Scanner(System.in);
    private static final String ARCHIVO_PERSISTENCIA = "gestioncolegios/datos.dat";
    private static DireccionComunal direccionComunal;

    public static void main(String[] args) {
        direccionComunal = cargarDatosDesdeArchivo();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== SISTEMA DE GESTIÓN EDUCACIONAL =====");
            System.out.println("1) Dirección Comunal");
            System.out.println("2) Colegios");
            System.out.println("3) Cursos");
            System.out.println("4) Profesores");
            System.out.println("5) Estudiantes");
            System.out.println("6) Estadísticas / Reportes");
            System.out.println("7) Guardar información");
            System.out.println("8) Salir");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuDireccionComunal();
                    break;
                case 2:
                    menuColegios();
                    break;
                case 3:
                    menuCursos();
                    break;
                case 4:
                    menuProfesores();
                    break;
                case 5:
                    menuEstudiantes();
                    break;
                case 6:
                    menuEstadisticas();
                    break;
                case 7:
                    guardarInformacion();
                    break;
                case 8:
                    guardarInformacion();
                    salir = true;
                    System.out.println("Sistema cerrado.");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuDireccionComunal() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ DIRECCIÓN COMUNAL =====");
            System.out.println("1) Ver información de la Dirección Comunal");
            System.out.println("2) Listar colegios");
            System.out.println("3) Ver estadísticas comunales");
            System.out.println("4) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    direccionComunal.mostrarInformacionDireccionComunal();
                    break;
                case 2:
                    listarColegios();
                    break;
                case 3:
                    mostrarEstadisticasComunal();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuColegios() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ COLEGIOS =====");
            System.out.println("1) Registrar colegio");
            System.out.println("2) Seleccionar colegio");
            System.out.println("3) Listar colegios");
            System.out.println("4) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarColegio();
                    break;
                case 2:
                    Colegio colegio = seleccionarColegio();
                    if (colegio != null) {
                        menuColegio(colegio);
                    }
                    break;
                case 3:
                    listarColegios();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuColegio(Colegio colegio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ COLEGIO: " + colegio.getNombre() + " =====");
            System.out.println("1) Ver información");
            System.out.println("2) Modificar datos");
            System.out.println("3) Eliminar colegio");
            System.out.println("4) Gestionar cursos");
            System.out.println("5) Estadísticas del colegio");
            System.out.println("6) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    colegio.mostrarInformacionColegio();
                    break;
                case 2:
                    modificarColegio(colegio);
                    break;
                case 3:
                    eliminarColegio(colegio);
                    volver = true;
                    break;
                case 4:
                    gestionarCursos(colegio);
                    break;
                case 5:
                    mostrarEstadisticasColegio(colegio);
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuCursos() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados. Registre un colegio primero.");
            return;
        }

        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ CURSOS =====");
            System.out.println("1) Crear curso en un colegio");
            System.out.println("2) Seleccionar curso existente");
            System.out.println("3) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    Colegio colegio = seleccionarColegio();
                    if (colegio != null) {
                        crearCursoEnColegio(colegio);
                    }
                    break;
                case 2:
                    Colegio colegioConCurso = seleccionarColegio();
                    if (colegioConCurso != null) {
                        Curso curso = seleccionarCurso(colegioConCurso);
                        if (curso != null) {
                            menuCurso(curso, colegioConCurso);
                        }
                    }
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuProfesores() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ PROFESORES =====");
            System.out.println("1) Registrar profesor");
            System.out.println("2) Consultar profesor");
            System.out.println("3) Asignar profesor a curso");
            System.out.println("4) Ver carga académica");
            System.out.println("5) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarProfesor();
                    break;
                case 2:
                    consultarProfesor();
                    break;
                case 3:
                    asignarProfesorACurso();
                    break;
                case 4:
                    mostrarCargaAcademica();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuEstudiantes() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ ESTUDIANTES =====");
            System.out.println("1) Registrar estudiante");
            System.out.println("2) Seleccionar estudiante");
            System.out.println("3) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarEstudiante();
                    break;
                case 2:
                    seleccionarEstudianteParaMenu();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuEstadisticas() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ ESTADÍSTICAS Y REPORTES =====");
            System.out.println("1) Estadísticas por curso");
            System.out.println("2) Estadísticas por colegio");
            System.out.println("3) Estadísticas comunales");
            System.out.println("4) Informe individual de estudiante");
            System.out.println("5) Nómina de aprobados/reprobados por curso");
            System.out.println("6) Ranking de cursos por colegio");
            System.out.println("7) Ranking de colegios");
            System.out.println("8) Comparación de colegios");
            System.out.println("9) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarEstadisticasPorCurso();
                    break;
                case 2:
                    mostrarEstadisticasPorColegio();
                    break;
                case 3:
                    mostrarEstadisticasComunal();
                    break;
                case 4:
                    mostrarInformeIndividual();
                    break;
                case 5:
                    mostrarNominaAprobadosReprobados();
                    break;
                case 6:
                    mostrarRankingCursos();
                    break;
                case 7:
                    mostrarRankingColegios();
                    break;
                case 8:
                    compararColegios();
                    break;
                case 9:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void registrarColegio() {
        System.out.println("\n===== REGISTRAR COLEGIO =====");
        System.out.print("Código establecimiento: ");
        String codigo = entrada.nextLine();
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Dirección: ");
        String direccion = entrada.nextLine();
        System.out.print("Teléfono: ");
        String telefono = entrada.nextLine();

        Colegio colegio = new Colegio(codigo, nombre, direccion, telefono);

        System.out.print("RUT del director: ");
        String rutDirector = entrada.nextLine();
        System.out.print("Nombre del director: ");
        String nombreDirector = entrada.nextLine();
        System.out.print("Teléfono del director: ");
        int telefonoDirector = leerEntero(entrada, "");
        System.out.print("Correo del director: ");
        String correoDirector = entrada.nextLine();

        Director director = new Director(rutDirector, nombreDirector, telefonoDirector, correoDirector);
        colegio.asignarDirector(director);
        direccionComunal.agregarColegio(colegio);

        System.out.println("Colegio registrado correctamente.");
    }

    private static void modificarColegio(Colegio colegio) {
        System.out.println("\n===== MODIFICAR COLEGIO =====");
        System.out.println("Dejar campo vacío para mantener el valor actual.");

        System.out.print("Nuevo nombre (actual: " + colegio.getNombre() + "): ");
        String nombre = entrada.nextLine();
        if (!nombre.isBlank()) {
            colegio.setNombre(nombre);
        }

        System.out.print("Nueva dirección (actual: " + colegio.getDireccion() + "): ");
        String direccion = entrada.nextLine();
        if (!direccion.isBlank()) {
            colegio.setDireccion(direccion);
        }

        System.out.print("Nuevo teléfono (actual: " + colegio.getTelefono() + "): ");
        String telefono = entrada.nextLine();
        if (!telefono.isBlank()) {
            colegio.setTelefono(telefono);
        }

        if (colegio.getDirector() != null) {
            System.out.print("Nuevo nombre director (actual: " + colegio.getDirector().getNombreCompleto() + "): ");
            String nombreDirector = entrada.nextLine();
            if (!nombreDirector.isBlank()) {
                colegio.getDirector().setNombreCompleto(nombreDirector);
            }

            System.out.print("Nuevo correo director (actual: " + colegio.getDirector().getCorreo() + "): ");
            String correoDirector = entrada.nextLine();
            if (!correoDirector.isBlank()) {
                colegio.getDirector().setCorreo(correoDirector);
            }

            System.out.print("Nuevo teléfono director (actual: " + colegio.getDirector().getTelefono() + "): ");
            String telefonoDirector = entrada.nextLine();
            if (!telefonoDirector.isBlank()) {
                int telefonoDirectorInt = Integer.parseInt(telefonoDirector);
                colegio.getDirector().setTelefono(telefonoDirectorInt);
            }
        }

        System.out.println("Datos del colegio actualizados.");
    }

    private static void eliminarColegio(Colegio colegio) {
        System.out.print("¿Está seguro que desea eliminar el colegio " + colegio.getNombre() + "? 1) Sí 2) No: ");
        int opcion = leerEntero(entrada, "");
        if (opcion == 1) {
            direccionComunal.getColegios().remove(colegio);
            System.out.println("Colegio eliminado.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private static void crearCursoEnColegio(Colegio colegio) {
        System.out.println("\n===== CREAR CURSO EN " + colegio.getNombre() + " =====");
        System.out.print("Código del curso: ");
        String codigoCurso = entrada.nextLine();
        System.out.print("Nivel o nombre del curso: ");
        String nombreNivel = entrada.nextLine();

        Curso curso = new Curso(codigoCurso, nombreNivel);
        System.out.print("Capacidad máxima de estudiantes (actual 30): ");
        int maxima = leerEntero(entrada, "");
        if (maxima > 0) {
            curso.setCantidadMaximaEstudiantes(maxima);
        }

        colegio.agregarCursos(curso);
        System.out.println("Curso registrado correctamente.");
    }

    private static void gestionarCursos(Colegio colegio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== CURSOS DEL COLEGIO: " + colegio.getNombre() + " =====");
            System.out.println("1) Crear curso");
            System.out.println("2) Seleccionar curso");
            System.out.println("3) Listar cursos");
            System.out.println("4) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearCursoEnColegio(colegio);
                    break;
                case 2:
                    Curso curso = seleccionarCurso(colegio);
                    if (curso != null) {
                        menuCurso(curso, colegio);
                    }
                    break;
                case 3:
                    listarCursos(colegio);
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuCurso(Curso curso, Colegio colegio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ CURSO: " + curso.getNombreNivel() + " (" + curso.getCodigoCurso() + ") =====");
            System.out.println("1) Ver información del curso");
            System.out.println("2) Asignar profesor jefe");
            System.out.println("3) Agregar estudiante");
            System.out.println("4) Eliminar estudiante");
            System.out.println("5) Registrar asistencia");
            System.out.println("6) Gestionar calificaciones");
            System.out.println("7) Estadísticas del curso");
            System.out.println("8) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    curso.mostrarInformacionCurso();
                    break;
                case 2:
                    asignarProfesorJefe(curso);
                    break;
                case 3:
                    agregarEstudiante(curso);
                    break;
                case 4:
                    eliminarEstudiante(curso);
                    break;
                case 5:
                    registrarAsistencia(curso);
                    break;
                case 6:
                    menuCalificaciones(curso);
                    break;
                case 7:
                    mostrarEstadisticasCurso(curso);
                    break;
                case 8:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void menuCalificaciones(Curso curso) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ CALIFICACIONES =====");
            System.out.println("1) Registrar notas por asignatura");
            System.out.println("2) Modificar notas existentes");
            System.out.println("3) Registrar nota final semestral");
            System.out.println("4) Generar acta de calificaciones del curso");
            System.out.println("5) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    registrarCalificaciones(curso);
                    break;
                case 2:
                    modificarCalificaciones(curso);
                    break;
                case 3:
                    registrarNotaFinalSemestral(curso);
                    break;
                case 4:
                    generarActaCalificaciones(curso);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void asignarProfesorJefe(Curso curso) {
        System.out.println("\n===== ASIGNAR PROFESOR JEFE =====");
        if (direccionComunal.getProfesores().isEmpty()) {
            System.out.println("No hay profesores registrados. Primero registre un profesor.");
            return;
        }

        Profesor profesor = seleccionarProfesor();
        if (profesor == null) {
            return;
        }

        curso.agregarProfesorJefe(profesor);
        direccionComunal.agregarProfesor(profesor);
        guardarInformacion();
        System.out.println("Profesor jefe asignado al curso.");
    }

    private static void registrarProfesor() {
        System.out.println("\n===== REGISTRAR PROFESOR =====");
        System.out.print("RUT: ");
        String rut = entrada.nextLine();

        if (buscarProfesorPorRut(rut) != null) {
            System.out.println("Ya existe un profesor con ese RUT.");
            return;
        }

        System.out.print("Nombre completo: ");
        String nombre = entrada.nextLine();
        System.out.print("Correo: ");
        String correo = entrada.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = entrada.nextLine();
        System.out.print("Teléfono: ");
        String telefono = entrada.nextLine();

        Profesor profesor = new Profesor(rut, nombre, correo, especialidad, telefono);
        direccionComunal.agregarProfesor(profesor);
        guardarInformacion();
        System.out.println("Profesor registrado correctamente.");
    }

    private static void consultarProfesor() {
        System.out.println("\n===== CONSULTAR PROFESOR =====");
        Profesor profesor = seleccionarProfesor();
        if (profesor != null) {
            profesor.mostrarInformacion();
        }
    }

    private static void asignarProfesorACurso() {
        System.out.println("\n===== ASIGNAR PROFESOR A CURSO =====");
        if (direccionComunal.getProfesores().isEmpty()) {
            System.out.println("No hay profesores registrados. Primero registre un profesor.");
            return;
        }
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios ni cursos registrados.");
            return;
        }

        Profesor profesor = seleccionarProfesor();
        if (profesor == null) {
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        curso.agregarProfesorJefe(profesor);
        System.out.println("Profesor asignado al curso.");
    }

    private static void mostrarCargaAcademica() {
        System.out.println("\n===== CARGA ACADÉMICA DEL PROFESOR =====");
        Profesor profesor = seleccionarProfesor();
        if (profesor == null) {
            return;
        }

        System.out.println("Profesor: " + profesor.getNombreCompleto());
        System.out.println("Cursos asignados: " + profesor.getCursosAsignados().size());
        for (Curso curso : profesor.getCursosAsignados()) {
            System.out.println("- " + curso.getCodigoCurso() + " | " + curso.getNombreNivel());
        }
    }

    private static void registrarEstudiante() {
        System.out.println("\n===== REGISTRAR ESTUDIANTE =====");
        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        System.out.print("RUT: ");
        String rut = entrada.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = entrada.nextLine();
        System.out.print("Fecha de nacimiento: ");
        String fechaNacimiento = entrada.nextLine();

        Estudiante estudiante = new Estudiante(rut, nombre, fechaNacimiento, curso);
        curso.agregarEstudiante(estudiante);
        System.out.println("Estudiante registrado correctamente.");
    }

    private static void seleccionarEstudianteParaMenu() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante != null) {
            menuEstudiante(estudiante);
        }
    }

    private static void menuEstudiante(Estudiante estudiante) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n===== MENÚ ESTUDIANTE: " + estudiante.getNombreCompleto() + " =====");
            System.out.println("1) Ver información");
            System.out.println("2) Actualizar datos");
            System.out.println("3) Consultar historial académico");
            System.out.println("4) Visualizar promedio general");
            System.out.println("5) Volver");

            int opcion = leerEntero(entrada, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    estudiante.mostrarInformacion();
                    break;
                case 2:
                    actualizarEstudiante(estudiante);
                    break;
                case 3:
                    estudiante.generarReporte();
                    break;
                case 4:
                    System.out.println("Promedio general: " + estudiante.calcularPromedioAnualTotal());
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private static void actualizarEstudiante(Estudiante estudiante) {
        System.out.println("\n===== ACTUALIZAR ESTUDIANTE =====");
        System.out.print("Nuevo nombre completo (actual: " + estudiante.getNombreCompleto() + "): ");
        String nombre = entrada.nextLine();
        if (!nombre.isBlank()) {
            estudiante.setNombreCompleto(nombre);
        }

        System.out.print("Nueva fecha de nacimiento (actual: " + estudiante.getFechaNacimiento() + "): ");
        String fechaNacimiento = entrada.nextLine();
        if (!fechaNacimiento.isBlank()) {
            estudiante.setFechaNacimiento(fechaNacimiento);
        }

        System.out.println("Datos del estudiante actualizados.");
    }

    private static void mostrarEstadisticasPorCurso() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        mostrarEstadisticasCurso(curso);
    }

    private static void mostrarEstadisticasPorColegio() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        mostrarEstadisticasColegio(colegio);
    }

    private static void mostrarInformeIndividual() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante != null) {
            estudiante.generarReporte();
        }
    }

    private static void mostrarNominaAprobadosReprobados() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        Curso curso = seleccionarCurso(colegio);
        if (curso == null) {
            return;
        }

        System.out.println("\n===== NÓMINA POR CURSO: " + curso.getNombreNivel() + " =====");
        for (Estudiante estudiante : curso.getEstudiantes()) {
            String estado = estudiante.aprobo() ? "Aprobado" : "Reprobado";
            System.out.println("- " + estudiante.getNombreCompleto() + " | Promedio: " + estudiante.calcularPromedioAnualTotal() + " | " + estado);
        }
    }

    private static void mostrarEstadisticasCurso(Curso curso) {
        System.out.println("\n===== ESTADÍSTICAS DEL CURSO " + curso.getNombreNivel() + " =====");
        System.out.println("Promedio general del curso: " + curso.calcularPromedioCurso());
        System.out.println("Mejor promedio del curso: " + curso.mejorPromedioEstudiante());
        System.out.println("Estudiante con mayor asistencia: " + estudianteConMayorAsistencia(curso));
        System.out.println("Porcentaje de aprobación: " + curso.porcentajeAprobadosCurso() + "%");
        System.out.println("Cantidad de estudiantes reprobados: " + cantidadEstudiantesReprobados(curso));
        System.out.println("Porcentaje de asistencia promedio: " + curso.porcentajeAsistenciaCurso() + "%");
        System.out.println("Cantidad de estudiantes inscritos: " + curso.getEstudiantes().size());
    }

    private static int cantidadEstudiantesReprobados(Curso curso) {
        int reprobados = 0;
        for (Estudiante estudiante : curso.getEstudiantes()) {
            if (!estudiante.aprobo()) {
                reprobados++;
            }
        }
        return reprobados;
    }

    private static String estudianteConMayorAsistencia(Curso curso) {
        if (curso.getEstudiantes().isEmpty()) {
            return "No hay estudiantes registrados";
        }

        Estudiante mejorEstudiante = curso.getEstudiantes().get(0);
        for (Estudiante estudiante : curso.getEstudiantes()) {
            if (estudiante.getporcentajeAsistencia() > mejorEstudiante.getporcentajeAsistencia()) {
                mejorEstudiante = estudiante;
            }
        }
        return mejorEstudiante.getNombreCompleto() + " (" + mejorEstudiante.getporcentajeAsistencia() + "%" + ")";
    }

    private static void mostrarEstadisticasColegio(Colegio colegio) {
        System.out.println("\n===== ESTADÍSTICAS DEL COLEGIO " + colegio.getNombre() + " =====");
        System.out.println("Promedio institucional: " + colegio.promedioInstitucional());
        System.out.println("Porcentaje de aprobación del colegio: " + colegio.porcentajeAprobacionColegio() + "%");
        System.out.println("Promedio de asistencia del colegio: " + colegio.porcentajeAsistenciaColegio() + "%");
        System.out.println("Cursos registrados: " + colegio.getCursos().size());
    }

    private static void mostrarEstadisticasComunal() {
        System.out.println("\n===== ESTADÍSTICAS COMUNALES =====");
        direccionComunal.mostrarInformacionDireccionComunal();
        System.out.println("Promedio comunal de aprobación: " + direccionComunal.porcentajePromedioComunal() + "%");
        System.out.println("Promedio comunal de asistencia: " + direccionComunal.porcentajeAsistenciaComunal() + "%");
    }

    private static void mostrarRankingCursos() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        Colegio colegio = seleccionarColegio();
        if (colegio == null) {
            return;
        }

        ArrayList<Curso> cursos = new ArrayList<>(colegio.getCursos());
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados en este colegio.");
            return;
        }

        cursos.sort((c1, c2) -> Double.compare(c2.calcularPromedioCurso(), c1.calcularPromedioCurso()));
        System.out.println("\n===== RANKING DE CURSOS EN " + colegio.getNombre() + " =====");
        for (int i = 0; i < cursos.size(); i++) {
            Curso curso = cursos.get(i);
            System.out.println((i + 1) + ") " + curso.getNombreNivel() + " - Promedio: " + curso.calcularPromedioCurso());
        }
    }

    private static void mostrarRankingColegios() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        ArrayList<Colegio> colegios = new ArrayList<>(direccionComunal.getColegios());
        colegios.sort((c1, c2) -> Double.compare(c2.promedioInstitucional(), c1.promedioInstitucional()));

        System.out.println("\n===== RANKING DE COLEGIOS =====");
        for (int i = 0; i < colegios.size(); i++) {
            Colegio colegio = colegios.get(i);
            System.out.println((i + 1) + ") " + colegio.getNombre() + " - Promedio institucional: " + colegio.promedioInstitucional());
        }
    }

    private static void compararColegios() {
        if (direccionComunal.getColegios().size() < 2) {
            System.out.println("Se necesitan al menos dos colegios para comparar.");
            return;
        }

        System.out.println("\n===== COMPARACIÓN DE COLEGIOS =====");
        listarColegios();
        int primero = leerEntero(entrada, "Seleccione el primer colegio: ") - 1;
        int segundo = leerEntero(entrada, "Seleccione el segundo colegio: ") - 1;

        if (primero < 0 || primero >= direccionComunal.getColegios().size() || segundo < 0 || segundo >= direccionComunal.getColegios().size() || primero == segundo) {
            System.out.println("Selección inválida.");
            return;
        }

        Colegio colegio1 = direccionComunal.getColegios().get(primero);
        Colegio colegio2 = direccionComunal.getColegios().get(segundo);

        System.out.println("\nComparación entre " + colegio1.getNombre() + " y " + colegio2.getNombre() + ":");
        System.out.println("Promedio institucional: " + colegio1.promedioInstitucional() + " vs " + colegio2.promedioInstitucional());
        System.out.println("Porcentaje de aprobación: " + colegio1.porcentajeAprobacionColegio() + "% vs " + colegio2.porcentajeAprobacionColegio() + "%");
        System.out.println("Promedio de asistencia: " + colegio1.porcentajeAsistenciaColegio() + "% vs " + colegio2.porcentajeAsistenciaColegio() + "%");
    }

    private static Colegio seleccionarColegio() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return null;
        }

        listarColegios();
        int seleccion = leerEntero(entrada, "Seleccione el colegio: ") - 1;
        if (seleccion < 0 || seleccion >= direccionComunal.getColegios().size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return direccionComunal.getColegios().get(seleccion);
    }

    private static Curso seleccionarCurso(Colegio colegio) {
        if (colegio.getCursos().isEmpty()) {
            System.out.println("No hay cursos registrados en este colegio.");
            return null;
        }

        listarCursos(colegio);
        int seleccion = leerEntero(entrada, "Seleccione el curso: ") - 1;
        if (seleccion < 0 || seleccion >= colegio.getCursos().size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return colegio.getCursos().get(seleccion);
    }

    private static Estudiante seleccionarEstudiante(Curso curso) {
        if (curso.getEstudiantes().isEmpty()) {
            System.out.println("No hay estudiantes registrados en este curso.");
            return null;
        }

        for (int i = 0; i < curso.getEstudiantes().size(); i++) {
            System.out.println((i + 1) + ") " + curso.getEstudiantes().get(i).getNombreCompleto());
        }

        int seleccion = leerEntero(entrada, "Seleccione el estudiante: ") - 1;
        if (seleccion < 0 || seleccion >= curso.getEstudiantes().size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return curso.getEstudiantes().get(seleccion);
    }

    private static Profesor seleccionarProfesor() {
        if (direccionComunal.getProfesores().isEmpty()) {
            System.out.println("No hay profesores registrados.");
            return null;
        }

        for (int i = 0; i < direccionComunal.getProfesores().size(); i++) {
            Profesor profesor = direccionComunal.getProfesores().get(i);
            System.out.println((i + 1) + ") " + profesor.getNombreCompleto() + " (" + profesor.getRut() + ")");
        }

        int seleccion = leerEntero(entrada, "Seleccione el profesor: ") - 1;
        if (seleccion < 0 || seleccion >= direccionComunal.getProfesores().size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return direccionComunal.getProfesores().get(seleccion);
    }

    private static Profesor buscarProfesorPorRut(String rut) {
        for (Profesor profesor : direccionComunal.getProfesores()) {
            if (profesor.getRut().equalsIgnoreCase(rut)) {
                return profesor;
            }
        }
        return null;
    }

    private static void agregarEstudiante(Curso curso) {
        System.out.println("\n===== AGREGAR ESTUDIANTE =====");
        System.out.print("RUT: ");
        String rut = entrada.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = entrada.nextLine();
        System.out.print("Fecha de nacimiento: ");
        String fechaNacimiento = entrada.nextLine();

        Estudiante estudiante = new Estudiante(rut, nombre, fechaNacimiento, curso);
        curso.agregarEstudiante(estudiante);
        System.out.println("Estudiante agregado al curso.");
    }

    private static void eliminarEstudiante(Curso curso) {
        System.out.println("\n===== ELIMINAR ESTUDIANTE =====");
        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante == null) {
            return;
        }

        curso.eliminarEstudiante(estudiante);
        System.out.println("Estudiante eliminado del curso.");
    }

    private static void registrarAsistencia(Curso curso) {
        System.out.println("\n===== REGISTRAR ASISTENCIA =====");
        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante == null) {
            return;
        }

        System.out.print("Fecha de asistencia: ");
        String fecha = entrada.nextLine();
        System.out.print("¿Está presente? 1) Sí 2) No: ");
        int opcion = leerEntero(entrada, "");
        boolean presente = opcion == 1;
        System.out.print("Asignatura: ");
        String asignatura = entrada.nextLine();
        boolean justificada = false;
        if (!presente) {
            System.out.print("¿La inasistencia está justificada? 1) Sí 2) No: ");
            int opcionJustificada = leerEntero(entrada, "");
            justificada = opcionJustificada == 1;
        }

        Asistencia asistencia = new Asistencia(fecha, presente, asignatura, justificada);
        estudiante.agregarAsistencia(asistencia);
        guardarInformacion();
        System.out.println("Asistencia registrada.");
    }

    private static void registrarCalificaciones(Curso curso) {
        System.out.println("\n===== REGISTRAR CALIFICACIONES =====");
        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante == null) {
            return;
        }

        System.out.print("Asignatura: ");
        String asignatura = entrada.nextLine();
        Calificaciones calificaciones = new Calificaciones(asignatura);

        System.out.print("Ingrese nota del primer semestre: ");
        double nota1 = leerDouble(entrada, "");
        calificaciones.RegistrarCalificacion(nota1, 1);

        System.out.print("Ingrese nota del segundo semestre: ");
        double nota2 = leerDouble(entrada, "");
        calificaciones.RegistrarCalificacion(nota2, 2);

        estudiante.agregarCalificaciones(calificaciones);
        guardarInformacion();
        System.out.println("Calificaciones registradas.");
    }

    private static void modificarCalificaciones(Curso curso) {
        System.out.println("\n===== MODIFICAR CALIFICACIONES =====");
        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante == null) {
            return;
        }

        if (estudiante.getRegistrarNotas().isEmpty()) {
            System.out.println("Este estudiante no tiene calificaciones registradas.");
            return;
        }

        for (int i = 0; i < estudiante.getRegistrarNotas().size(); i++) {
            Calificaciones c = estudiante.getRegistrarNotas().get(i);
            System.out.println((i + 1) + ") " + c.getAsignatura());
        }

        int seleccion = leerEntero(entrada, "Seleccione la asignatura a modificar: ") - 1;
        if (seleccion < 0 || seleccion >= estudiante.getRegistrarNotas().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Calificaciones calificacion = estudiante.getRegistrarNotas().get(seleccion);
        System.out.println("Asignatura seleccionada: " + calificacion.getAsignatura());
        System.out.println("1) Modificar nota primer semestre");
        System.out.println("2) Modificar nota segundo semestre");
        int semestre = leerEntero(entrada, "Seleccione semestre: ");

        ArrayList<Double> notas = (semestre == 1) ? calificacion.getNotasPrimerSemestre() : calificacion.getNotasSegundoSemestre();
        if (notas.isEmpty()) {
            System.out.println("No existen notas registradas para ese semestre.");
            return;
        }

        for (int i = 0; i < notas.size(); i++) {
            System.out.println((i + 1) + ") " + notas.get(i));
        }

        int indice = leerEntero(entrada, "Seleccione la nota a modificar: ") - 1;
        if (indice < 0 || indice >= notas.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        double nuevaNota = leerDouble(entrada, "Ingrese la nueva nota: ");
        calificacion.modificarCalificacion(indice, nuevaNota, semestre);
        guardarInformacion();
        System.out.println("Nota modificada.");
    }

    private static void registrarNotaFinalSemestral(Curso curso) {
        System.out.println("\n===== REGISTRAR NOTA FINAL SEMESTRAL =====");
        Estudiante estudiante = seleccionarEstudiante(curso);
        if (estudiante == null) {
            return;
        }

        if (estudiante.getRegistrarNotas().isEmpty()) {
            System.out.println("Este estudiante no tiene calificaciones registradas.");
            return;
        }

        for (int i = 0; i < estudiante.getRegistrarNotas().size(); i++) {
            Calificaciones c = estudiante.getRegistrarNotas().get(i);
            System.out.println((i + 1) + ") " + c.getAsignatura());
        }

        int seleccion = leerEntero(entrada, "Seleccione la asignatura: ") - 1;
        if (seleccion < 0 || seleccion >= estudiante.getRegistrarNotas().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Calificaciones calificacion = estudiante.getRegistrarNotas().get(seleccion);
        System.out.println("Asignatura seleccionada: " + calificacion.getAsignatura());
        System.out.println("1) Registrar nota final primer semestre");
        System.out.println("2) Registrar nota final segundo semestre");
        int semestre = leerEntero(entrada, "Seleccione semestre: ");
        double notaFinal = leerDouble(entrada, "Ingrese nota final semestral: ");
        calificacion.RegistrarCalificacionSemestral(notaFinal, semestre);
        guardarInformacion();
        System.out.println("Nota final semestral registrada.");
    }

    private static void generarActaCalificaciones(Curso curso) {
        System.out.println("\n===== ACTA DE CALIFICACIONES DEL CURSO " + curso.getNombreNivel() + " =====");
        for (Estudiante estudiante : curso.getEstudiantes()) {
            System.out.println("Estudiante: " + estudiante.getNombreCompleto());
            if (estudiante.getRegistrarNotas().isEmpty()) {
                System.out.println("  No hay calificaciones registradas.");
                continue;
            }
            for (Calificaciones calificacion : estudiante.getRegistrarNotas()) {
                System.out.println("  Asignatura: " + calificacion.getAsignatura());
                System.out.println("    Notas primer semestre: " + calificacion.getNotasPrimerSemestre());
                System.out.println("    Nota final primer semestre: " + calificacion.getNotaFinalSemestral1());
                System.out.println("    Notas segundo semestre: " + calificacion.getNotasSegundoSemestre());
                System.out.println("    Nota final segundo semestre: " + calificacion.getNotaFinalSemestral2());
                System.out.println("    Promedio anual: " + calificacion.calcularPromedioAnual());
            }
        }
    }

    private static void listarColegios() {
        if (direccionComunal.getColegios().isEmpty()) {
            System.out.println("No hay colegios registrados.");
            return;
        }

        System.out.println("\n===== COLEGIOS REGISTRADOS =====");
        for (int i = 0; i < direccionComunal.getColegios().size(); i++) {
            Colegio colegio = direccionComunal.getColegios().get(i);
            System.out.println((i + 1) + ") " + colegio.getNombre() + " (" + colegio.getCodigoEstablecimiento() + ")");
        }
    }

    private static void listarCursos(Colegio colegio) {
        if (colegio.getCursos().isEmpty()) {
            System.out.println("No hay cursos registrados en este colegio.");
            return;
        }

        System.out.println("\n===== CURSOS EN " + colegio.getNombre() + " =====");
        for (int i = 0; i < colegio.getCursos().size(); i++) {
            Curso curso = colegio.getCursos().get(i);
            System.out.println((i + 1) + ") " + curso.getNombreNivel() + " (" + curso.getCodigoCurso() + ")");
        }
    }

    private static void guardarInformacion() {
        File archivo = new File(ARCHIVO_PERSISTENCIA);
        archivo.getParentFile().mkdirs();

        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(direccionComunal);
            System.out.println("Información guardada en " + ARCHIVO_PERSISTENCIA);
        } catch (IOException e) {
            System.out.println("Error al guardar la información: " + e.getMessage());
        }
    }

    private static DireccionComunal cargarDatosDesdeArchivo() {
        File archivo = new File(ARCHIVO_PERSISTENCIA);

        if (!archivo.exists()) {
            return new DireccionComunal("DAEM Arica");
        }

        try (ObjectInputStream entradaObjetos = new ObjectInputStream(new FileInputStream(archivo))) {
            Object objeto = entradaObjetos.readObject();
            if (objeto instanceof DireccionComunal) {
                DireccionComunal direccion = (DireccionComunal) objeto;
                if (direccion.getProfesores() == null) {
                    direccion.setProfesores(new ArrayList<>());
                }
                reconstruirProfesoresDesdeDatos(direccion);
                System.out.println("Datos cargados desde archivo.");
                return direccion;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar la información: " + e.getMessage());
        }
        return new DireccionComunal("DAEM Arica");
    }

    private static void reconstruirProfesoresDesdeDatos(DireccionComunal direccion) {
        if (direccion.getProfesores() == null) {
            direccion.setProfesores(new ArrayList<>());
        } else {
            direccion.getProfesores().clear();
        }

        for (Colegio colegio : direccion.getColegios()) {
            for (Curso curso : colegio.getCursos()) {
                Profesor jefe = curso.getProfesorGefe();
                if (jefe != null && !profesorExiste(direccion, jefe.getRut())) {
                    direccion.agregarProfesor(jefe);
                }
            }
        }
    }

    private static boolean profesorExiste(DireccionComunal direccion, String rut) {
        for (Profesor profesor : direccion.getProfesores()) {
            if (profesor.getRut().equalsIgnoreCase(rut)) {
                return true;
            }
        }
        return false;
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            if (!mensaje.isBlank()) {
                System.out.print(mensaje);
            }
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            if (!mensaje.isBlank()) {
                System.out.print(mensaje);
            }
            String linea = scanner.nextLine();
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número decimal válido.");
            }
        }
    }
}
