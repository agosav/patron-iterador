package com.example.demo.entidades;

import com.example.demo.interfaces.Iterador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDeCliente {
    private Integer id;
    private LocalDateTime fechaDeEncuesta;
    private RespuestaPosible respuestaSeleccionada;

    public List<String> mostrarDatosCompletos() {
        String descripcionRespuesta = respuestaSeleccionada.getDescripcion();
        List<String> preguntaYEncuesta = respuestaSeleccionada.obtenerTuPreguntaYEncuesta();

        List<String> resultado = new ArrayList<>();
        resultado.add(descripcionRespuesta);
        resultado.addAll(preguntaYEncuesta);

        return resultado;
    }
}
