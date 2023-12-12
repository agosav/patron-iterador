package com.example.demo.controllers;

import com.example.demo.entidades.*;
import com.example.demo.interfaces.IAgregado;
import com.opencsv.CSVWriter;
import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GestorConsultarEncuesta implements IAgregado {

    private List<Llamada> todasLasLlamadas;
    private Llamada llamadaSeleccionada;
    private List<Llamada> llamadasFiltradas;
    private Cliente cliente;
    private Estado estadoActual;
    private String nombreEstadoActual;
    private String encuesta;
    private List<String> preguntas;
    private List<String> respuestas;
    private double duracion;
    private LocalDateTime fechaInicioPeriodo;
    private LocalDateTime fechaFinPeriodo;

    public GestorConsultarEncuesta(List<Llamada> todasLasLlamadas) {
        this.todasLasLlamadas = todasLasLlamadas;
    }

    @Override
    public IteradorLlamada crearIterador(List<Object> elementos, List<Object> filtros) {
        if (elementos != null && !elementos.isEmpty()) {
            Object primerElemento = elementos.get(0);

            if (primerElemento != null) {
                Class<?> tipoElemento = elementos.get(0).getClass();

                if (tipoElemento.equals(Llamada.class)) {
                    return new IteradorLlamada(elementos, filtros);
                }
            } else {
                throw new IllegalArgumentException("El primer elemento es nulo");
            }
        }
        throw new IllegalArgumentException("Tipo de elemento no compatible.");
    }

    public List<Llamada> tomarPeriodoLlamada(LocalDateTime fechaInicioPeriodo, LocalDateTime fechaFinPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.fechaFinPeriodo = fechaFinPeriodo;

        return buscarLlamadasEnPeriodo();
    }

    public List<Llamada> buscarLlamadasEnPeriodo() {

        // Acá hay que buscar en la base de datos todas las llamadas
        List<Llamada> llamadas = todasLasLlamadas;
        List<Object> elementos = new ArrayList<>(llamadas);

        // Acá empieza el patrón

        List<Object> filtros = List.of(this.fechaInicioPeriodo, this.fechaFinPeriodo);
        IteradorLlamada iterador = crearIterador(elementos, filtros);
        ArrayList<Llamada> filtradas = new ArrayList<>();

        iterador.primero();

        // Loop buscar llamadas en período
        while (!iterador.haTerminado()) {
            Llamada llamadaActual = iterador.actual();

            if (iterador.cumpleFiltro()) {
                filtradas.add(llamadaActual);
            }

            iterador.siguiente();
        }
        this.llamadasFiltradas = filtradas;

        return filtradas;
    }

    public void tomarSeleccionLlamada(Llamada llamadaElegida) {
        this.llamadaSeleccionada = llamadaElegida;
        getDatosLlamadaSeleccionada();
    }

    public void getDatosLlamadaSeleccionada() {
        this.cliente = llamadaSeleccionada.getCliente();
        this.estadoActual = llamadaSeleccionada.getEstadoActual();
        this.nombreEstadoActual = estadoActual.getNombreEstado();
        this.duracion = llamadaSeleccionada.calcularDuracion();

        List<List<String>> datos = llamadaSeleccionada.getDatosLlamadaSeleccionada();

        List<String> res = new ArrayList<>();
        List<String> preg = new ArrayList<>();

        for (List<String> dato : datos) {
            res.add(dato.get(0));
            preg.add(dato.get(1));
        }

        this.respuestas = res;
        this.encuesta = datos.get(0).get(2);
        this.preguntas = preg;
    }

    public void tomarOpcionGenerarCSV() {
        generarCSV();
    }

    public void generarCSV() {
        String archivoCSV = "csv/Llamada_" + llamadaSeleccionada.getId() + "_Cliente_" + cliente.getDni() + ".csv";

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(archivoCSV), '|', CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Encabezado
            String[] encabezados = {"Nombre del cliente", "Estado actual de la llamada", "Duración de la llamada"};
            csvWriter.writeNext(encabezados);

            // Datos de la llamada
            String[] filaLlamada = {cliente.getNombreCompleto(), estadoActual.getNombreEstado(), String.valueOf(duracion)};
            csvWriter.writeNext(filaLlamada);

            // Preguntas y respuestas
            List<List<String>> datos = llamadaSeleccionada.getDatosLlamadaSeleccionada();
            for (List<String> dato : datos) {
                String descripcionPregunta = dato.get(1);
                String descripcionRespuesta = dato.get(0);

                String[] filaPregunta = {descripcionPregunta, descripcionRespuesta};
                csvWriter.writeNext(filaPregunta);
            }
            System.out.println("CSV generado con éxito en: " + archivoCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

