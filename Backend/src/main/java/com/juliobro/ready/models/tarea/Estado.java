package com.juliobro.ready.models.tarea;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {
    PENDIENTE("pendiente"),
    EN_PROCESO("en proceso"),
    COMPLETADA("completada");

    private final String minusculas;

    Estado(String minusculas) {
        this.minusculas = minusculas;
    }

    @JsonValue //Esto es para que Jackson serialice un objeto a JSON utilizando los valores en minúscula
    public String getMinusculas() { //La idea de esto es mantener un mismo formato para el intercambio de datos JSON
        return minusculas;
    }
}