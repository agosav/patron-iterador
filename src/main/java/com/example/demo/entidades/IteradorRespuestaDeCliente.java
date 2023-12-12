package com.example.demo.entidades;

import com.example.demo.interfaces.Iterador;

import java.util.ArrayList;
import java.util.List;

public class IteradorRespuestaDeCliente implements Iterador<RespuestaDeCliente, Object> {

    private int posicionActual;
    private final List<RespuestaDeCliente> respuestasDeCliente;

    public IteradorRespuestaDeCliente(List<Object> elementos) {
        this.respuestasDeCliente = new ArrayList<>();
        for (Object elemento : elementos) {
            if (elemento instanceof RespuestaDeCliente) {
                this.respuestasDeCliente.add((RespuestaDeCliente) elemento);
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
    public RespuestaDeCliente actual() {
        return respuestasDeCliente.get(posicionActual);
    }

    @Override
    public boolean haTerminado() {
        return posicionActual == respuestasDeCliente.size();
    }

    @Override
    public boolean cumpleFiltro() {
        return false;
    }
}
