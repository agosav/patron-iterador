package com.example.demo.entidades;

import com.example.demo.interfaces.IAgregado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Llamada implements IAgregado {
    private Integer id;
    private String descripcionOperador;
    private String detalleAccionRequerida;
    private String observacionAuditor;
    private Cliente cliente;
    private List<RespuestaDeCliente> respuestasDeCliente;
    private List<CambioEstado> cambiosEstado;

    public double calcularDuracion() {
        CambioEstado inicial = null;
        CambioEstado finalC = null;

        for (int i = 0; i < Objects.requireNonNull(cambiosEstado).size(); i++) {
            if (cambiosEstado.get(i).esEstadoInicial()) {
                inicial = cambiosEstado.get(i);
            }
            if (cambiosEstado.get(i).esEstadoFinal()) {
                finalC = cambiosEstado.get(i);
            }
        }

        assert inicial != null;
        assert finalC != null;
        Duration duracion = Duration.between(inicial.getFechaHoraInicio(), finalC.getFechaHoraInicio());
        return duracion.getSeconds();
    }

    public boolean esDePeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        CambioEstado inicial = null;
        CambioEstado finalC = null;

        for (CambioEstado cambio : cambiosEstado) {
            if (cambio.esEstadoInicial()) inicial = cambio;
            if (cambio.esEstadoFinal()) finalC = cambio;
        }

        assert inicial != null;
        assert finalC != null;
        return inicial.getFechaHoraInicio().isAfter(fechaInicio) && finalC.getFechaHoraInicio().isBefore(fechaFin);

    }

    public boolean tieneEncuestaRespondida() {
        return respuestasDeCliente != null;
    }

    public Estado getEstadoActual() {
        cambiosEstado.sort(Comparator.comparing(CambioEstado::getFechaHoraInicio).reversed());

        if (!cambiosEstado.isEmpty()) {
            return cambiosEstado.get(0).getEstado();
        } else {
            return null;
        }
    }

    public List<List<String>> getDatosLlamadaSeleccionada() {
        List<RespuestaDeCliente> respuestas = respuestasDeCliente;
        List<Object> filtros = List.of();
        List<Object> elementos = new ArrayList<>(respuestas);
        List<List<String>> datos = new ArrayList<>();

        IteradorRespuestaDeCliente iterador = crearIterador(elementos, filtros);

        iterador.primero();

        while (!iterador.haTerminado()) {
            RespuestaDeCliente respuesta = iterador.actual();

            datos.add(respuesta.mostrarDatosCompletos());

            iterador.siguiente();
        }

        return datos;
    }

    @Override
    public IteradorRespuestaDeCliente crearIterador(List<Object> elementos, List<Object> filtros) {
        if (elementos != null && !elementos.isEmpty()) {
            Object primerElemento = elementos.get(0);

            if (primerElemento != null) {
                Class<?> tipoElemento = elementos.get(0).getClass();

                if (tipoElemento.equals(RespuestaDeCliente.class)) {
                    return new IteradorRespuestaDeCliente(elementos);
                }
            } else {
                throw new IllegalArgumentException("El primer elemento es nulo");
            }
        }
        throw new IllegalArgumentException("Tipo de elemento no compatible.");
    }
}
