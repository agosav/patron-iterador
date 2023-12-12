module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.data.commons;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.opencsv;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.entidades;
    exports com.example.demo.controllers;
    exports com.example.demo.interfaces;
}