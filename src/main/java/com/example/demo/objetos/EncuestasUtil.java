package com.example.demo.objetos;

import com.example.demo.entidades.Encuesta;
import com.example.demo.entidades.Pregunta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class EncuestasUtil {

    public static List<Encuesta> obtenerEncuestas() {
        List<Pregunta> preguntas = PreguntasUtil.obtenerPreguntas();
        Pregunta pregunta1 = preguntas.get(0);
        Pregunta pregunta2 = preguntas.get(1);
        Pregunta pregunta3 = preguntas.get(2);
        Pregunta pregunta4 = preguntas.get(3);
        Pregunta pregunta5 = preguntas.get(4);
        Pregunta pregunta6 = preguntas.get(5);
        Pregunta pregunta7 = preguntas.get(6);
        Pregunta pregunta8 = preguntas.get(7);
        Pregunta pregunta9 = preguntas.get(8);
        Encuesta encuesta1 = new Encuesta(1, "Encuesta sobre operador", LocalDateTime.parse("2023-11-17T10:15:30"), Arrays.asList(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5));
        Encuesta encuesta2 = new Encuesta(2, "Encuesta sobre servicio", LocalDateTime.parse("2023-11-17T10:15:30"), Arrays.asList(pregunta6, pregunta7, pregunta8, pregunta9));
        return Arrays.asList(encuesta1, encuesta2);
    }
}
