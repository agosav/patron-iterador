package com.example.demo;

import com.example.demo.controllers.GestorConsultarEncuesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DatosController {
    private GestorConsultarEncuesta gestor;

    @FXML
    private Button btnCSV;

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
    void handleButtonClicks(ActionEvent event) {
        if (event.getSource() == btnVolver) {
            btnVolver.getScene().getWindow().hide();
        }
        if (event.getSource() == btnCSV) {
            gestor.tomarOpcionGenerarCSV();
            abrirPopupExito();
        }
    }

    public void abrirPopupExito() {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("csv-exito.fxml"));
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

    public void mostrarDatos(GestorConsultarEncuesta gestor) {
        this.gestor = gestor;

        celular.setText(gestor.getCliente().getNroCelular());
        descripcion.setText(gestor.getEncuesta());
        dni.setText("" + gestor.getCliente().getDni());
        duracion.setText("" + gestor.getDuracion());
        estadoActual.setText(gestor.getEstadoActual().getNombreEstado());
        nombre.setText(gestor.getCliente().getNombreCompleto());
        pregunta.getItems().addAll(gestor.getPreguntas());
        pregunta.setOnAction(this::mostrarRespuesta);
    }

    public void mostrarRespuesta(ActionEvent event) {
        List<List<String>> datos = gestor.getLlamadaSeleccionada().getDatosLlamadaSeleccionada();
        for (List<String> dato : datos) {
            if (Objects.equals(dato.get(1), pregunta.getValue())) {
                respuesta.setText(dato.get(0));
            }
        }
    }
}