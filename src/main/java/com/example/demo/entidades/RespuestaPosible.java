package com.example.demo.entidades;

import com.example.demo.interfaces.IAgregado;
import com.example.demo.objetos.PreguntasUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPosible implements IAgregado {
    private Integer id;
    private String descripcion;
    private int valor;

    public List<String> obtenerTuPreguntaYEncuesta() {

        List<String> resultado = new ArrayList<>();

        List<Object> elementos = new ArrayList<>(PreguntasUtil.obtenerPreguntas());
        List<Object> filtros = new ArrayList<>();

        IteradorPregunta iterador = crearIterador(elementos, filtros);

        iterador.primero();

        Pregunta preguntaBuscada = null;

        while (!iterador.haTerminado()) {
            Pregunta preg = iterador.actual();

            if (preg.esTuRespuesta(this)) {
                preguntaBuscada = preg;
            }

            iterador.siguiente();
        }

        assert preguntaBuscada != null;
        resultado.add(preguntaBuscada.getDescripcion());
        resultado.add(preguntaBuscada.getDescripcionEncuesta());

        return resultado;
    }

    @Override
    public IteradorPregunta crearIterador(List<Object> elementos, List<Object> filtros) {
        if (elementos != null && !elementos.isEmpty()) {
            Object primerElemento = elementos.get(0);

            if (primerElemento != null) {
                Class<?> tipoElemento = elementos.get(0).getClass();

                if (tipoElemento.equals(Pregunta.class)) {
                    return new IteradorPregunta(elementos);
                }
            } else {
                throw new IllegalArgumentException("El primer elemento es nulo");
            }
        }
        throw new IllegalArgumentException("Tipo de elemento no compatible.");
    }
}
