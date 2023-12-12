package com.example.demo.interfaces;

public interface Iterador<T, W> {
    void primero();
    void siguiente();
    T actual();
    boolean haTerminado();
    boolean cumpleFiltro();
}
