package com.juliobro.ready.models.tarea;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {
    PENDIENTE("pendiente"),
    EN_PROCESO("en proceso"),
    COMPLETADA("completada");

    private final String minusculas;

    Estado(String minusculas) {
        this.minusculas = minusculas;
    }

    // -- La idea de los siguientes métodos es mantener un mismo formato para el intercambio de datos JSON

    @JsonValue //Esto es para que Jackson serialice un objeto a JSON utilizando los valores en minúscula
    public String getMinusculas() {
        return minusculas;
    }

    @JsonCreator //Esto es para que Jackson deserialice un JSON a objeto verificando los valores en minúscula
    public static Estado fromJson(String valor) {
        for (Estado estado : Estado.values()) {
            if (estado.minusculas.trim().equalsIgnoreCase(valor)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado desconocido para: '" + valor +
                "'. Los únicos estados posibles son: 'pendiente', 'en proceso' y 'completada'");
    }
}
