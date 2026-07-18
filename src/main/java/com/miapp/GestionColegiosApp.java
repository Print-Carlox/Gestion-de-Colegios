package com.miapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;

/**
 * Interfaz gráfica simple (JavaFX) para el Sistema de Gestión Educacional.
 * Reutiliza las clases del modelo (Colegio, Curso, Estudiante, Profesor,
 * Director, Asistencia, Calificaciones, DireccionComunal) tal como fueron
 * entregadas, sin modificarlas.
 *
 * La interfaz está organizada en pestañas (TabPane), una por cada
 * funcionalidad del menú original de consola (probar.java).
 */
public class GestionColegiosApp extends Application {

    private static final String ARCHIVO_PERSISTENCIA = "datos.dat";

    private DireccionComunal direccionComunal;
    private TextArea areaSalida;

    // Combos que necesitan actualizarse cuando se agregan colegios/cursos
    private ComboBox<Colegio> comboColegioParaCurso;
    private ComboBox<Curso> comboCursoParaProfesor;
    private ComboBox<Curso> comboCursoParaEstudiante;
    private ComboBox<Curso> comboCursoParaAsistencia;
    private ComboBox<Estudiante> comboEstudianteParaAsistencia;
    private ComboBox<Curso> comboCursoParaCalificaciones;
    private ComboBox<Estudiante> comboEstudianteParaCalificaciones;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        direccionComunal = cargarDatosDesdeArchivo();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Colegios", crearPanelColegio()));
        tabPane.getTabs().add(new Tab("Cursos", crearPanelCurso()));
        tabPane.getTabs().add(new Tab("Profesores", crearPanelProfesor()));
        tabPane.getTabs().add(new Tab("Estudiantes", crearPanelEstudiante()));
        tabPane.getTabs().add(new Tab("Asistencia", crearPanelAsistencia()));
        tabPane.getTabs().add(new Tab("Calificaciones", crearPanelCalificaciones()));
        tabPane.getTabs().add(new Tab("Reportes", crearPanelReportes()));
        for (Tab t : tabPane.getTabs()) {
            t.setClosable(false);
        }

        Label titulo = new Label("Sistema de Gestión Educacional - DAEM Arica");

        areaSalida = new TextArea();
        areaSalida.setEditable(false);
        areaSalida.setPrefRowCount(8);

        Button botonGuardar = new Button("Guardar información");
        botonGuardar.setOnAction(e -> guardarInformacion());

        HBox barraInferior = new HBox(10, botonGuardar);
        barraInferior.setPadding(new Insets(5));

        VBox panelInferior = new VBox(5, barraInferior, areaSalida);
        panelInferior.setPadding(new Insets(5));

        BorderPane root = new BorderPane();
        root.setTop(titulo);
        root.setCenter(tabPane);
        root.setBottom(panelInferior);
        BorderPane.setMargin(titulo, new Insets(10));

        stage.setTitle("Gestión de Colegios");
        stage.setScene(new Scene(root, 700, 600));
        stage.setOnCloseRequest(e -> guardarInformacion());
        stage.show();
    }

    // ===================== PESTAÑA COLEGIO =====================

    private GridPane crearPanelColegio() {
        GridPane grid = crearGridBase();

        TextField campoCodigo = new TextField();
        TextField campoNombre = new TextField();
        TextField campoDireccion = new TextField();
        TextField campoTelefono = new TextField();
        TextField campoRutDirector = new TextField();
        TextField campoNombreDirector = new TextField();
        TextField campoTelefonoDirector = new TextField();
        TextField campoCorreoDirector = new TextField();

        int fila = 0;
        grid.add(new Label("Código establecimiento:"), 0, fila);
        grid.add(campoCodigo, 1, fila++);
        grid.add(new Label("Nombre:"), 0, fila);
        grid.add(campoNombre, 1, fila++);
        grid.add(new Label("Dirección:"), 0, fila);
        grid.add(campoDireccion, 1, fila++);
        grid.add(new Label("Teléfono:"), 0, fila);
        grid.add(campoTelefono, 1, fila++);
        grid.add(new Separator(), 0, fila, 2, 1);
        fila++;
        grid.add(new Label("RUT director:"), 0, fila);
        grid.add(campoRutDirector, 1, fila++);
        grid.add(new Label("Nombre director:"), 0, fila);
        grid.add(campoNombreDirector, 1, fila++);
        grid.add(new Label("Teléfono director:"), 0, fila);
        grid.add(campoTelefonoDirector, 1, fila++);
        grid.add(new Label("Correo director:"), 0, fila);
        grid.add(campoCorreoDirector, 1, fila++);

        Button botonRegistrar = new Button("Registrar Colegio");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            try {
                int telefonoDirector = Integer.parseInt(campoTelefonoDirector.getText().trim());

                Colegio colegio = new Colegio(campoCodigo.getText(), campoNombre.getText(),
                        campoDireccion.getText(), campoTelefono.getText());

                Director director = new Director(campoRutDirector.getText(), campoNombreDirector.getText(),
                        telefonoDirector, campoCorreoDirector.getText());
                colegio.asignarDirector(director);

                direccionComunal.agregarColegio(colegio);
                actualizarComboColegios();

                mostrarSalida("Colegio registrado: " + colegio.getNombre());
                campoCodigo.clear();
                campoNombre.clear();
                campoDireccion.clear();
                campoTelefono.clear();
                campoRutDirector.clear();
                campoNombreDirector.clear();
                campoTelefonoDirector.clear();
                campoCorreoDirector.clear();
            } catch (NumberFormatException ex) {
                mostrarError("El teléfono del director debe ser un número entero.");
            }
        });

        return grid;
    }

    // ===================== PESTAÑA CURSO =====================

    private GridPane crearPanelCurso() {
        GridPane grid = crearGridBase();

        comboColegioParaCurso = new ComboBox<>();
        configurarComboColegios(comboColegioParaCurso);
        TextField campoCodigoCurso = new TextField();
        TextField campoNivel = new TextField();

        int fila = 0;
        grid.add(new Label("Colegio:"), 0, fila);
        grid.add(comboColegioParaCurso, 1, fila++);
        grid.add(new Label("Código del curso:"), 0, fila);
        grid.add(campoCodigoCurso, 1, fila++);
        grid.add(new Label("Nivel:"), 0, fila);
        grid.add(campoNivel, 1, fila++);

        Button botonRegistrar = new Button("Registrar Curso");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            Colegio colegio = comboColegioParaCurso.getValue();
            if (colegio == null) {
                mostrarError("Debe seleccionar un colegio.");
                return;
            }
            Curso curso = new Curso(campoCodigoCurso.getText(), campoNivel.getText());
            colegio.agregarCursos(curso);
            actualizarComboCursos();

            mostrarSalida("Curso registrado: " + curso.getCodigoCurso() + " en " + colegio.getNombre());
            campoCodigoCurso.clear();
            campoNivel.clear();
        });

        return grid;
    }

    // ===================== PESTAÑA PROFESOR =====================

    private GridPane crearPanelProfesor() {
        GridPane grid = crearGridBase();

        comboCursoParaProfesor = new ComboBox<>();
        configurarComboCursos(comboCursoParaProfesor);
        TextField campoRut = new TextField();
        TextField campoNombre = new TextField();
        TextField campoCorreo = new TextField();
        TextField campoEspecialidad = new TextField();
        TextField campoTelefono = new TextField();

        int fila = 0;
        grid.add(new Label("Curso:"), 0, fila);
        grid.add(comboCursoParaProfesor, 1, fila++);
        grid.add(new Label("RUT:"), 0, fila);
        grid.add(campoRut, 1, fila++);
        grid.add(new Label("Nombre completo:"), 0, fila);
        grid.add(campoNombre, 1, fila++);
        grid.add(new Label("Correo:"), 0, fila);
        grid.add(campoCorreo, 1, fila++);
        grid.add(new Label("Especialidad:"), 0, fila);
        grid.add(campoEspecialidad, 1, fila++);
        grid.add(new Label("Teléfono:"), 0, fila);
        grid.add(campoTelefono, 1, fila++);

        Button botonRegistrar = new Button("Registrar Profesor Jefe");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            Curso curso = comboCursoParaProfesor.getValue();
            if (curso == null) {
                mostrarError("Debe seleccionar un curso.");
                return;
            }
            Profesor profesor = new Profesor(campoRut.getText(), campoNombre.getText(),
                    campoCorreo.getText(), campoEspecialidad.getText(), campoTelefono.getText());
            curso.agregarProfesorJefe(profesor);

            mostrarSalida("Profesor " + profesor.getNombreCompleto() + " asignado al curso " + curso.getCodigoCurso());
            campoRut.clear();
            campoNombre.clear();
            campoCorreo.clear();
            campoEspecialidad.clear();
            campoTelefono.clear();
        });

        return grid;
    }

    // ===================== PESTAÑA ESTUDIANTE =====================

    private GridPane crearPanelEstudiante() {
        GridPane grid = crearGridBase();

        comboCursoParaEstudiante = new ComboBox<>();
        configurarComboCursos(comboCursoParaEstudiante);
        TextField campoRut = new TextField();
        TextField campoNombre = new TextField();
        TextField campoFechaNacimiento = new TextField();
        campoFechaNacimiento.setPromptText("dd-mm-aaaa");

        int fila = 0;
        grid.add(new Label("Curso:"), 0, fila);
        grid.add(comboCursoParaEstudiante, 1, fila++);
        grid.add(new Label("RUT:"), 0, fila);
        grid.add(campoRut, 1, fila++);
        grid.add(new Label("Nombre completo:"), 0, fila);
        grid.add(campoNombre, 1, fila++);
        grid.add(new Label("Fecha de nacimiento:"), 0, fila);
        grid.add(campoFechaNacimiento, 1, fila++);

        Button botonRegistrar = new Button("Registrar Estudiante");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            Curso curso = comboCursoParaEstudiante.getValue();
            if (curso == null) {
                mostrarError("Debe seleccionar un curso.");
                return;
            }
            Estudiante estudiante = new Estudiante(campoRut.getText(), campoNombre.getText(),
                    campoFechaNacimiento.getText(), curso);
            curso.agregarEstudiante(estudiante);

            mostrarSalida("Estudiante " + estudiante.getNombreCompleto() + " agregado al curso " + curso.getCodigoCurso());
            campoRut.clear();
            campoNombre.clear();
            campoFechaNacimiento.clear();
        });

        return grid;
    }

    // ===================== PESTAÑA ASISTENCIA =====================

    private GridPane crearPanelAsistencia() {
        GridPane grid = crearGridBase();

        comboCursoParaAsistencia = new ComboBox<>();
        configurarComboCursos(comboCursoParaAsistencia);
        comboEstudianteParaAsistencia = new ComboBox<>();
        configurarComboEstudiantes(comboEstudianteParaAsistencia);

        comboCursoParaAsistencia.setOnAction(e ->
                actualizarComboEstudiantes(comboCursoParaAsistencia.getValue(), comboEstudianteParaAsistencia));

        TextField campoFecha = new TextField();
        campoFecha.setPromptText("dd-mm-aaaa");
        TextField campoAsignatura = new TextField();
        CheckBox checkPresente = new CheckBox("Presente");
        checkPresente.setSelected(true);
        CheckBox checkJustificada = new CheckBox("Justificada");

        int fila = 0;
        grid.add(new Label("Curso:"), 0, fila);
        grid.add(comboCursoParaAsistencia, 1, fila++);
        grid.add(new Label("Estudiante:"), 0, fila);
        grid.add(comboEstudianteParaAsistencia, 1, fila++);
        grid.add(new Label("Fecha:"), 0, fila);
        grid.add(campoFecha, 1, fila++);
        grid.add(new Label("Asignatura:"), 0, fila);
        grid.add(campoAsignatura, 1, fila++);
        grid.add(checkPresente, 1, fila++);
        grid.add(checkJustificada, 1, fila++);

        Button botonRegistrar = new Button("Registrar Asistencia");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            Estudiante estudiante = comboEstudianteParaAsistencia.getValue();
            if (estudiante == null) {
                mostrarError("Debe seleccionar un curso y un estudiante.");
                return;
            }
            Asistencia asistencia = new Asistencia(campoFecha.getText(), checkPresente.isSelected(),
                    campoAsignatura.getText(), checkJustificada.isSelected());
            estudiante.agregarAsistencia(asistencia);

            mostrarSalida("Asistencia registrada para " + estudiante.getNombreCompleto()
                    + " (" + campoAsignatura.getText() + ", " + campoFecha.getText() + ")");
            campoFecha.clear();
            campoAsignatura.clear();
            checkPresente.setSelected(true);
            checkJustificada.setSelected(false);
        });

        return grid;
    }

    // ===================== PESTAÑA CALIFICACIONES =====================

    private GridPane crearPanelCalificaciones() {
        GridPane grid = crearGridBase();

        comboCursoParaCalificaciones = new ComboBox<>();
        configurarComboCursos(comboCursoParaCalificaciones);
        comboEstudianteParaCalificaciones = new ComboBox<>();
        configurarComboEstudiantes(comboEstudianteParaCalificaciones);

        comboCursoParaCalificaciones.setOnAction(e ->
                actualizarComboEstudiantes(comboCursoParaCalificaciones.getValue(), comboEstudianteParaCalificaciones));

        TextField campoAsignatura = new TextField();
        TextField campoNota1 = new TextField();
        TextField campoNota2 = new TextField();

        int fila = 0;
        grid.add(new Label("Curso:"), 0, fila);
        grid.add(comboCursoParaCalificaciones, 1, fila++);
        grid.add(new Label("Estudiante:"), 0, fila);
        grid.add(comboEstudianteParaCalificaciones, 1, fila++);
        grid.add(new Label("Asignatura:"), 0, fila);
        grid.add(campoAsignatura, 1, fila++);
        grid.add(new Label("Nota 1er semestre:"), 0, fila);
        grid.add(campoNota1, 1, fila++);
        grid.add(new Label("Nota 2do semestre:"), 0, fila);
        grid.add(campoNota2, 1, fila++);

        Button botonRegistrar = new Button("Registrar Calificaciones");
        grid.add(botonRegistrar, 1, fila++);

        botonRegistrar.setOnAction(e -> {
            Estudiante estudiante = comboEstudianteParaCalificaciones.getValue();
            if (estudiante == null) {
                mostrarError("Debe seleccionar un curso y un estudiante.");
                return;
            }
            try {
                double nota1 = Double.parseDouble(campoNota1.getText().trim());
                double nota2 = Double.parseDouble(campoNota2.getText().trim());

                Calificaciones calificaciones = new Calificaciones(campoAsignatura.getText());
                calificaciones.RegistrarCalificacion(nota1, 1);
                calificaciones.RegistrarCalificacion(nota2, 2);
                estudiante.agregarCalificaciones(calificaciones);

                mostrarSalida("Calificaciones de " + campoAsignatura.getText() + " registradas para "
                        + estudiante.getNombreCompleto());
                campoAsignatura.clear();
                campoNota1.clear();
                campoNota2.clear();
            } catch (NumberFormatException ex) {
                mostrarError("Las notas deben ser valores numéricos entre 1.0 y 7.0.");
            }
        });

        return grid;
    }

    // ===================== PESTAÑA REPORTES =====================

    private VBox crearPanelReportes() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));

        Button botonReporteCompleto = new Button("Mostrar reporte completo");
        Button botonEstadisticasComunales = new Button("Mostrar estadísticas comunales");

        botonReporteCompleto.setOnAction(e -> mostrarSalida(generarReporteCompleto()));
        botonEstadisticasComunales.setOnAction(e -> mostrarSalida(generarEstadisticasComunales()));

        panel.getChildren().addAll(botonReporteCompleto, botonEstadisticasComunales);
        return panel;
    }

    private String generarReporteCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTES DEL SISTEMA =====\n");
        sb.append("Dirección comunal: ").append(direccionComunal.getNombreDireccionComunal()).append("\n");
        sb.append("Colegios registrados: ").append(direccionComunal.getColegios().size()).append("\n\n");

        for (Colegio colegio : direccionComunal.getColegios()) {
            sb.append("--- Colegio: ").append(colegio.getNombre())
                    .append(" (").append(colegio.getCodigoEstablecimiento()).append(") ---\n");
            sb.append("Director: ").append(colegio.getDirector() != null
                    ? colegio.getDirector().getNombreCompleto() : "No asignado").append("\n");
            sb.append("Cursos: ").append(colegio.getCursos().size()).append("\n");

            for (Curso curso : colegio.getCursos()) {
                sb.append("  * Curso ").append(curso.getCodigoCurso()).append(" - ").append(curso.getNombreNivel())
                        .append(" | Estudiantes: ").append(curso.getEstudiantes().size())
                        .append(" | Promedio: ").append(String.format("%.1f", curso.calcularPromedioCurso()))
                        .append(" | % Aprobación: ").append(String.format("%.1f", curso.porcentajeAprobadosCurso()))
                        .append(" | % Asistencia: ").append(String.format("%.1f", curso.porcentajeAsistenciaCurso()))
                        .append("\n");
                for (Estudiante estudiante : curso.getEstudiantes()) {
                    sb.append("      - ").append(estudiante.getNombreCompleto())
                            .append(" | Promedio anual: ").append(String.format("%.1f", estudiante.calcularPromedioAnualTotal()))
                            .append(" | Asistencia: ").append(String.format("%.1f", estudiante.getporcentajeAsistencia())).append("%")
                            .append(" | ").append(estudiante.aprobo() ? "Aprobado" : "Reprobado")
                            .append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String generarEstadisticasComunales() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== ESTADÍSTICAS COMUNALES =====\n");
        sb.append("% Promedio de aprobación comunal: ")
                .append(String.format("%.1f", direccionComunal.porcentajePromedioComunal())).append("\n");
        sb.append("% Asistencia comunal: ")
                .append(String.format("%.1f", direccionComunal.porcentajeAsistenciaComunal())).append("\n");
        return sb.toString();
    }

    // ===================== UTILIDADES =====================

    private GridPane crearGridBase() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));
        return grid;
    }

    private void mostrarSalida(String mensaje) {
        areaSalida.appendText(mensaje + "\n");
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void configurarComboColegios(ComboBox<Colegio> combo) {
        combo.setConverter(new StringConverter<Colegio>() {
            @Override
            public String toString(Colegio colegio) {
                return colegio == null ? "" : colegio.getNombre();
            }
            @Override
            public Colegio fromString(String s) {
                return null;
            }
        });
        combo.setItems(FXCollections.observableArrayList(direccionComunal.getColegios()));
    }

    private void configurarComboCursos(ComboBox<Curso> combo) {
        combo.setConverter(new StringConverter<Curso>() {
            @Override
            public String toString(Curso curso) {
                return curso == null ? "" : curso.getCodigoCurso() + " - " + curso.getNombreNivel();
            }
            @Override
            public Curso fromString(String s) {
                return null;
            }
        });
        combo.setItems(FXCollections.observableArrayList(obtenerTodosLosCursos()));
    }

    private void configurarComboEstudiantes(ComboBox<Estudiante> combo) {
        combo.setConverter(new StringConverter<Estudiante>() {
            @Override
            public String toString(Estudiante estudiante) {
                return estudiante == null ? "" : estudiante.getNombreCompleto();
            }
            @Override
            public Estudiante fromString(String s) {
                return null;
            }
        });
        combo.setItems(FXCollections.observableArrayList());
    }

    private ObservableList<Curso> obtenerTodosLosCursos() {
        ObservableList<Curso> cursos = FXCollections.observableArrayList();
        for (Colegio colegio : direccionComunal.getColegios()) {
            cursos.addAll(colegio.getCursos());
        }
        return cursos;
    }

    private void actualizarComboEstudiantes(Curso curso, ComboBox<Estudiante> comboEstudiante) {
        if (curso == null) {
            comboEstudiante.setItems(FXCollections.observableArrayList());
        } else {
            comboEstudiante.setItems(FXCollections.observableArrayList(curso.getEstudiantes()));
        }
    }

    private void actualizarComboColegios() {
        comboColegioParaCurso.setItems(FXCollections.observableArrayList(direccionComunal.getColegios()));
    }

    private void actualizarComboCursos() {
        ObservableList<Curso> todosLosCursos = obtenerTodosLosCursos();
        comboCursoParaProfesor.setItems(FXCollections.observableArrayList(todosLosCursos));
        comboCursoParaEstudiante.setItems(FXCollections.observableArrayList(todosLosCursos));
        comboCursoParaAsistencia.setItems(FXCollections.observableArrayList(todosLosCursos));
        comboCursoParaCalificaciones.setItems(FXCollections.observableArrayList(todosLosCursos));
    }

    // ===================== PERSISTENCIA =====================

    private DireccionComunal cargarDatosDesdeArchivo() {
        File archivo = new File(ARCHIVO_PERSISTENCIA);
        if (!archivo.exists()) {
            return new DireccionComunal("DAEM Arica");
        }
        try (ObjectInputStream entradaObjetos = new ObjectInputStream(new FileInputStream(archivo))) {
            Object objeto = entradaObjetos.readObject();
            if (objeto instanceof DireccionComunal) {
                return (DireccionComunal) objeto;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Si no se puede cargar, se usa un sistema nuevo.
        }
        return new DireccionComunal("DAEM Arica");
    }

    private void guardarInformacion() {
        try (ObjectOutputStream salidaObjetos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PERSISTENCIA))) {
            salidaObjetos.writeObject(direccionComunal);
            mostrarSalida("Información guardada correctamente en " + ARCHIVO_PERSISTENCIA);
        } catch (IOException e) {
            mostrarError("No se pudo guardar la información: " + e.getMessage());
        }
    }
}