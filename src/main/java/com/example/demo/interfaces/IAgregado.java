package com.example.demo.interfaces;

import java.util.List;

public interface IAgregado {
    Iterador<?, Object> crearIterador(List<Object> elementos, List<Object> filtros);
}
