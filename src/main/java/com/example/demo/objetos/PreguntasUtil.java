package com.example.demo.objetos;

import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.RespuestaPosible;

import java.util.Arrays;
import java.util.List;

public class PreguntasUtil {

    public static List<Pregunta> obtenerPreguntas() {
        Pregunta pregunta1 = new Pregunta(1, "¿Cómo calificaría la atención del operador?", Arrays.asList(
                new RespuestaPosible(1, "Bien", 1),
                new RespuestaPosible(2, "Regular", 2),
                new RespuestaPosible(3, "Mala", 3)
        ));

        Pregunta pregunta2 = new Pregunta(2, "¿Las respuestas del operador fueron útiles?", Arrays.asList(
                new RespuestaPosible(4, "Sí", 1),
                new RespuestaPosible(5, "No", 2)
        ));
        Pregunta pregunta3 = new Pregunta(3, "¿El operador fue capaz de resolver su problema?", Arrays.asList(
                new RespuestaPosible(6, "Sí, completamente", 1),
                new RespuestaPosible(7, "En parte", 2),
                new RespuestaPosible(8, "No, en absoluto", 3)
        ));

        Pregunta pregunta4 = new Pregunta(4, "¿Cuánto tiempo tuvo que esperar para ser atendido por un operador?", Arrays.asList(
                new RespuestaPosible(9, "Menos de 1 minuto", 1),
                new RespuestaPosible(10, "1-5 minutos", 2),
                new RespuestaPosible(11, "Más de 5 minutos", 3)
        ));

        Pregunta pregunta5 = new Pregunta(5, "¿El operador fue amable y cortés durante la llamada?", Arrays.asList(
                new RespuestaPosible(12, "Sí, muy amable", 1),
                new RespuestaPosible(13, "Neutral", 2),
                new RespuestaPosible(14, "No, poco amable", 3)
        ));
        Pregunta pregunta6 = new Pregunta(6, "¿Cómo calificaría la calidad del servicio en general?", Arrays.asList(
                new RespuestaPosible(15, "Excelente", 1),
                new RespuestaPosible(16, "Bueno", 2),
                new RespuestaPosible(17, "Regular", 3),
                new RespuestaPosible(18, "Malo", 4)
        ));

        Pregunta pregunta7 = new Pregunta(7, "¿El servicio cumplió con sus expectativas?", Arrays.asList(
                new RespuestaPosible(19, "Sí, completamente", 1),
                new RespuestaPosible(20, "En parte", 2),
                new RespuestaPosible(21, "No, en absoluto", 3)
        ));

        Pregunta pregunta8 = new Pregunta(8, "¿Recomendaría nuestro servicio a otras personas?", Arrays.asList(
                new RespuestaPosible(22, "Definitivamente sí", 1),
                new RespuestaPosible(23, "Quizás", 2),
                new RespuestaPosible(24, "Definitivamente no", 3)
        ));

        Pregunta pregunta9 = new Pregunta(9, "¿Cómo calificaría la rapidez en la resolución de problemas o consultas?", Arrays.asList(
                new RespuestaPosible(25, "Rápida", 1),
                new RespuestaPosible(26, "Moderada", 2),
                new RespuestaPosible(27, "Lenta", 3)
        ));

        return Arrays.asList(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7, pregunta8, pregunta9);
    }
}
