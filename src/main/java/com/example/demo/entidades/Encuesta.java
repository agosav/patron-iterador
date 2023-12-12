package com.example.demo.entidades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {
    private Integer id;
    private String descripcion;
    private LocalDateTime fechaFinVigencia;
    private List<Pregunta> preguntas;

    public boolean esTuPregunta(Pregunta preg) {
        for (Pregunta pregunta : preguntas) {
            if (Objects.equals(pregunta, preg)) {
                return true;
            }
        }

        return false;
    }
}
