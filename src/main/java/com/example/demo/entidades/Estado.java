package com.example.demo.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
    private Integer id;
    private String nombreEstado;

    public Boolean esFinalizada() {
        return nombreEstado.equalsIgnoreCase("Finalizada");
    }

    public Boolean EsIniciada() {
        return nombreEstado.equalsIgnoreCase("Iniciada");
    }
}
