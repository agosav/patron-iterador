package com.example.demo.entidades;


import com.example.demo.interfaces.Iterador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IteradorLlamada implements Iterador<Llamada, Object> {

    private int posicionActual;
    private final List<Llamada> llamadas;
    private final List<Object> filtros;

    public IteradorLlamada(List<Object> elementos, List<Object> filtros) {
        this.llamadas = new ArrayList<>();
        for (Object elemento : elementos) {
            if (elemento instanceof Llamada) {
                this.llamadas.add((Llamada) elemento);
            }
        }
        this.filtros = filtros;
    }

    @Override
    public void primero() {
        posicionActual = 0;
    }

    @Override
    public void siguiente() {
        posicionActual++;
    }

    @Override
    public Llamada actual() {
        return llamadas.get(posicionActual);
    }

    @Override
    public boolean haTerminado() {
        return posicionActual == llamadas.size();
    }

    @Override
    public boolean cumpleFiltro() {
        LocalDateTime fechaInicioPeriodo = (LocalDateTime) filtros.get(0);
        LocalDateTime fechaFinPeriodo = (LocalDateTime) filtros.get(1);

        return actual().esDePeriodo(fechaInicioPeriodo, fechaFinPeriodo) && actual().tieneEncuestaRespondida();
    }
}
