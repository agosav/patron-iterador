package com.example.demo.entidades;

import com.example.demo.interfaces.Iterador;

import java.util.ArrayList;
import java.util.List;

public class IteradorPregunta implements Iterador<Pregunta, Object> {
    private int posicionActual;
    private final List<Pregunta> preguntas;

    public IteradorPregunta(List<Object> elementos) {
        this.preguntas = new ArrayList<>();
        for (Object elemento : elementos) {
            if (elemento instanceof Pregunta) {
                this.preguntas.add((Pregunta) elemento);
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
    public Pregunta actual() {
        return preguntas.get(posicionActual);
    }

    @Override
    public boolean haTerminado() {
        return posicionActual == preguntas.size();
    }

    @Override
    public boolean cumpleFiltro() {
        return false;
    }
}
