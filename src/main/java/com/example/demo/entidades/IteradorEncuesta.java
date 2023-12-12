package com.example.demo.entidades;

import com.example.demo.interfaces.Iterador;

import java.util.ArrayList;
import java.util.List;

public class IteradorEncuesta implements Iterador<Encuesta, Object> {
    private int posicionActual;
    private final List<Encuesta> encuestas;

    public IteradorEncuesta(List<Object> elementos) {
        this.encuestas = new ArrayList<>();
        for (Object elemento : elementos) {
            if (elemento instanceof Encuesta) {
                this.encuestas.add((Encuesta) elemento);
            }
        }
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
    public Encuesta actual() {
        return encuestas.get(posicionActual);
    }

    @Override
    public boolean haTerminado() {
        return posicionActual == encuestas.size();
    }

    @Override
    public boolean cumpleFiltro() {
        return false;
    }
}
