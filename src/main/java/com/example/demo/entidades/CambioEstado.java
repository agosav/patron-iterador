package com.example.demo.entidades;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstado {
    private LocalDateTime fechaHoraInicio;
    private Estado estado;
    public boolean esEstadoInicial() {
        return estado.EsIniciada();
    }
    public boolean esEstadoFinal() {
        return estado.esFinalizada();
    }
}
