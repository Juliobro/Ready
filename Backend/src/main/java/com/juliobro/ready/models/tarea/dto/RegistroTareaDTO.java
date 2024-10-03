package com.juliobro.ready.models.tarea.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegistroTareaDTO(

        @NotBlank(message = "{validation.titulo}")
        String titulo,

        String descripcion,

        @NotNull(message = "{validation.fecha-limite}")
        @Future(message = "{validation.fecha-limite-futura}")
        LocalDateTime fechaLimite
) {
}
