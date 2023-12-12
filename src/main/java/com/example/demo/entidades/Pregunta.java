package com.example.demo.entidades;

import com.example.demo.interfaces.IAgregado;
import com.example.demo.objetos.EncuestasUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta implements IAgregado {
    private Integer id;
    private String descripcion;
    private List<RespuestaPosible> respuestasPosibles;

    public boolean esTuRespuesta(RespuestaPosible res) {
        for (RespuestaPosible respuestaPosible : respuestasPosibles) {
            if (Objects.equals(respuestaPosible, res)) {
                return true;
            }
        }

        return false;
    }

    public String getDescripcionEncuesta() {

        List<Object> elementos = new ArrayList<>(EncuestasUtil.obtenerEncuestas());
        List<Object> filtros = new ArrayList<>();

        IteradorEncuesta iterador = crearIterador(elementos, filtros);

        Encuesta encuestaBuscada = null;
        iterador.primero();

        while (!iterador.haTerminado()) {
            Encuesta actual = iterador.actual();
            if (actual.esTuPregunta(this)) {
                encuestaBuscada = actual;
            }

            iterador.siguiente();
        }

        assert encuestaBuscada != null;
        return encuestaBuscada.getDescripcion();
    }

    @Override
    public IteradorEncuesta crearIterador(List<Object> elementos, List<Object> filtros) {
        if (elementos != null && !elementos.isEmpty()) {
            Object primerElemento = elementos.get(0);

            if (primerElemento != null) {
                Class<?> tipoElemento = elementos.get(0).getClass();

                if (tipoElemento.equals(Encuesta.class)) {
                    return new IteradorEncuesta(elementos);
                }
            } else {
                throw new IllegalArgumentException("El primer elemento es nulo");
            }
        }
        throw new IllegalArgumentException("Tipo de elemento no compatible.");
    }
}
