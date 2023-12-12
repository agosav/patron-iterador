package com.example.demo;

import com.example.demo.controllers.GestorConsultarEncuesta;
import com.example.demo.entidades.*;
import com.example.demo.objetos.PreguntasUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class HelloController implements Initializable {

    private GestorConsultarEncuesta gestor;
    private List<Llamada> llamadas;
    private LocalDateTime fechaInicioDate;
    private LocalDateTime fechaFinDate;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCerrar;

    @FXML
    private DatePicker fechaDesde;

    @FXML
    private DatePicker fechaHasta;

    @FXML
    private ListView<String> lista;
    @FXML
    private Button btnCSV;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnVolver;

    @FXML
    private Label celular;

    @FXML
    private Label descripcion;

    @FXML
    private Label dni;

    @FXML
    private Label duracion;

    @FXML
    private Label estadoActual;

    @FXML
    private Label nombre;

    @FXML
    private ComboBox<String> pregunta;

    @FXML
    private Label respuesta;
    @FXML
    private Button btnCerrarDeVerdad;

    @FXML
    public void handleButtonClicks(ActionEvent event) {
        if (event.getSource() == btnCerrar) {
            btnCerrar.getScene().getWindow().hide();
        }

        if (event.getSource() == btnVolver) {
            btnVolver.getScene().getWindow().hide();
        }

        if (event.getSource() == btnBuscar) {

            try {
                tomarFechaInicio();
                tomarFechaFin();

                List<Llamada> llamadasFiltradas = gestor.tomarPeriodoLlamada(fechaInicioDate, fechaFinDate);

                mostrarLlamadas(llamadasFiltradas);
            } catch (RuntimeException e) {
                abrirAdvertenciaNoLlamadas();
            }
        }
    }

    public void tomarFechaInicio() {
        this.fechaInicioDate = convertirFecha(fechaDesde.getEditor().getText());
    }

    public void tomarFechaFin() {
        this.fechaFinDate = convertirFecha(fechaHasta.getEditor().getText());
    }

    public void mostrarLlamadas(List<Llamada> llamadasFiltradas) {
        if (llamadasFiltradas.isEmpty()) {
            abrirAdvertenciaNoLlamadas();
        }

        lista.getItems().clear();

        List<String> llamadasFiltradasStrings = new ArrayList<>();
        for (Llamada llamada : llamadasFiltradas) {
            String llamadaString = String.format("ID: %d - Cliente: %s", llamada.getId(), llamada.getCliente().getNombreCompleto());
            llamadasFiltradasStrings.add(llamadaString);
        }

        lista.getItems().addAll(llamadasFiltradasStrings);
        this.llamadas = llamadasFiltradas;
    }

    public LocalDateTime convertirFecha(String fecha) {
        String[] componentes = fecha.split("/");
        int dia = Integer.parseInt(componentes[0]);
        int mes = Integer.parseInt(componentes[1]);
        int anio = Integer.parseInt(componentes[2]);
        return LocalDateTime.of(anio, mes, dia, 0, 0);
    }

    @FXML
    public void abrirVentanaLlamada() {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("datos.fxml"));
            Parent root = loader.load();

            DatosController datosController = loader.getController();
            datosController.mostrarDatos(gestor);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            root.setOnMousePressed(evt -> {
                xOffset.set(evt.getSceneX());
                yOffset.set(evt.getSceneY());
            });

            root.setOnMouseDragged(evt -> {
                stage.setX(evt.getScreenX() - xOffset.get());
                stage.setY(evt.getScreenY() - yOffset.get());
            });

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void abrirAdvertenciaNoLlamadas() {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("no-llamadas.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            root.setOnMousePressed(evt -> {
                xOffset.set(evt.getSceneX());
                yOffset.set(evt.getSceneY());
            });

            root.setOnMouseDragged(evt -> {
                stage.setX(evt.getScreenX() - xOffset.get());
                stage.setY(evt.getScreenY() - yOffset.get());
            });

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Llamada tomarSeleccionLlamada() {
        String item = lista.getSelectionModel().getSelectedItem();
        Pattern pattern = Pattern.compile("ID: (\\d+)");
        Matcher matcher = pattern.matcher(item);

        if (matcher.find()) {
            String idStr = matcher.group(1);
            int id = Integer.parseInt(idStr);
            for (Llamada llamada : llamadas) {
                if (llamada.getId().equals(id)) {
                    return llamada;
                }
            }
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lista.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Llamada llamadaElegida = tomarSeleccionLlamada();
                    gestor.tomarSeleccionLlamada(llamadaElegida);
                    abrirVentanaLlamada();
                    }
                }
            );
        } catch (NullPointerException ignored) {
        }

        List<Pregunta> preguntas = PreguntasUtil.obtenerPreguntas();
        Pregunta pregunta1 = preguntas.get(0);
        Pregunta pregunta2 = preguntas.get(1);
        Pregunta pregunta3 = preguntas.get(2);
        Pregunta pregunta4 = preguntas.get(3);
        Pregunta pregunta5 = preguntas.get(4);
        Pregunta pregunta6 = preguntas.get(5);
        Pregunta pregunta7 = preguntas.get(6);
        Pregunta pregunta8 = preguntas.get(7);
        Pregunta pregunta9 = preguntas.get(8);

        Cliente cliente1 = new Cliente(34657437, "Agostina Avalle", "123-456-669");
        Cliente cliente2 = new Cliente(34565343, "Juan Cruz López Freytas", "213-346-420");
        Cliente cliente3 = new Cliente(23486457, "Milton Romero", "987-654-321");
        Cliente cliente4 = new Cliente(47545645, "Cielo Bertoni", "754-365-666");
        Cliente cliente5 = new Cliente(47354783, "Brisa Fraga", "122-233-234");

        List<RespuestaDeCliente> respuestasDeCliente1 = Arrays.asList(
                new RespuestaDeCliente(1, LocalDateTime.parse("2023-11-17T10:15:00"), pregunta1.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(2, LocalDateTime.parse("2023-11-17T10:15:10"), pregunta2.getRespuestasPosibles().get(1)),
                new RespuestaDeCliente(3, LocalDateTime.parse("2023-11-17T10:15:20"), pregunta3.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(4, LocalDateTime.parse("2023-11-17T10:15:30"), pregunta4.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(5, LocalDateTime.parse("2023-11-17T10:15:40"), pregunta5.getRespuestasPosibles().get(1))
        );
        List<RespuestaDeCliente> respuestasDeCliente2 = Arrays.asList(
                new RespuestaDeCliente(6, LocalDateTime.parse("2023-11-17T10:15:00"), pregunta1.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(7, LocalDateTime.parse("2023-11-17T10:15:10"), pregunta2.getRespuestasPosibles().get(1)),
                new RespuestaDeCliente(8, LocalDateTime.parse("2023-11-17T10:15:20"), pregunta3.getRespuestasPosibles().get(1)),
                new RespuestaDeCliente(9, LocalDateTime.parse("2023-11-17T10:15:22"), pregunta4.getRespuestasPosibles().get(1)),
                new RespuestaDeCliente(10, LocalDateTime.parse("2023-11-17T10:15:40"), pregunta5.getRespuestasPosibles().get(0))
        );
        List<RespuestaDeCliente> respuestasDeCliente3 = Arrays.asList(
                new RespuestaDeCliente(10, LocalDateTime.parse("2023-11-17T10:15:00"), pregunta1.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(11, LocalDateTime.parse("2023-11-17T10:15:10"), pregunta2.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(12, LocalDateTime.parse("2023-11-17T10:15:20"), pregunta3.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(13, LocalDateTime.parse("2023-11-17T10:15:30"), pregunta4.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(14, LocalDateTime.parse("2023-11-17T10:15:40"), pregunta5.getRespuestasPosibles().get(1))
        );
        List<RespuestaDeCliente> respuestasDeCliente4 = Arrays.asList(
                new RespuestaDeCliente(15, LocalDateTime.parse("2023-11-17T10:15:10"), pregunta6.getRespuestasPosibles().get(3)),
                new RespuestaDeCliente(16, LocalDateTime.parse("2023-11-17T10:15:20"), pregunta7.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(17, LocalDateTime.parse("2023-11-17T10:15:30"), pregunta8.getRespuestasPosibles().get(2)),
                new RespuestaDeCliente(18, LocalDateTime.parse("2023-11-17T10:15:40"), pregunta9.getRespuestasPosibles().get(1))
        );
        List<RespuestaDeCliente> respuestasDeCliente5 = Arrays.asList(
                new RespuestaDeCliente(19, LocalDateTime.parse("2023-11-17T10:15:10"), pregunta6.getRespuestasPosibles().get(1)),
                new RespuestaDeCliente(20, LocalDateTime.parse("2023-11-17T10:15:20"), pregunta7.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(21, LocalDateTime.parse("2023-11-17T10:15:30"), pregunta8.getRespuestasPosibles().get(0)),
                new RespuestaDeCliente(22, LocalDateTime.parse("2023-11-17T10:15:40"), pregunta9.getRespuestasPosibles().get(1))
        );

        Estado estadoIniciada = new Estado(1, "Iniciada");
        Estado estadoFinalizada = new Estado(2, "Finalizada");

        List<CambioEstado> cambiosEstado1 = Arrays.asList(
                new CambioEstado(LocalDateTime.parse("2023-11-12T10:10:20"), estadoIniciada),
                new CambioEstado(LocalDateTime.parse("2023-11-12T10:15:30"), estadoFinalizada)
        );
        List<CambioEstado> cambiosEstado2 = Arrays.asList(
                new CambioEstado(LocalDateTime.parse("2023-11-10T10:15:30"), estadoIniciada),
                new CambioEstado(LocalDateTime.parse("2023-11-10T10:20:29"), estadoFinalizada)
        );
        List<CambioEstado> cambiosEstado3 = Arrays.asList(
                new CambioEstado(LocalDateTime.parse("2023-11-17T10:15:30"), estadoIniciada),
                new CambioEstado(LocalDateTime.parse("2023-11-17T10:17:29"), estadoFinalizada)
        );
        List<CambioEstado> cambiosEstado4 = Arrays.asList(
                new CambioEstado(LocalDateTime.parse("2023-11-18T10:10:30"), estadoIniciada),
                new CambioEstado(LocalDateTime.parse("2023-11-18T10:18:29"), estadoFinalizada)
        );
        List<CambioEstado> cambiosEstado5 = Arrays.asList(
                new CambioEstado(LocalDateTime.parse("2023-11-20T10:11:10"), estadoIniciada),
                new CambioEstado(LocalDateTime.parse("2023-11-20T10:17:30"), estadoFinalizada)
        );

        Llamada llamada1 = new Llamada(1, "Descripción del operador 1", "Detalles de la acción requerida 1",
                "Observaciones del auditor 1", cliente1, respuestasDeCliente1, cambiosEstado1);
        Llamada llamada2 = new Llamada(2, "Descripción del operador 2", "Detalles de la acción requerida 2",
                "Observaciones del auditor 2", cliente2, respuestasDeCliente2, cambiosEstado2);
        Llamada llamada3 = new Llamada(3, "Descripción del operador 3", "Detalles de la acción requerida 3",
                "Observaciones del auditor 3", cliente3, respuestasDeCliente3, cambiosEstado3);
        Llamada llamada4 = new Llamada(4, "Descripción del operador 4", "Detalles de la acción requerida 4",
                "Observaciones del auditor 4", cliente4, respuestasDeCliente4, cambiosEstado4);
        Llamada llamada5 = new Llamada(5, "Descripción del operador 5", "Detalles de la acción requerida 5",
                "Observaciones del auditor 5", cliente5, respuestasDeCliente5, cambiosEstado5);

        gestor = new GestorConsultarEncuesta(List.of(llamada1, llamada2, llamada3, llamada4, llamada5));
    }
}
