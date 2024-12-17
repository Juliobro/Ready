package com.juliobro.ready.domain.models.tarea.dto;

import com.juliobro.ready.domain.models.tarea.Estado;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActualizarTareaDTO(

        @NotBlank(message = "{validation.titulo}")
        String titulo,

        @NotNull(message = "{validation.fecha-limite}")
        @Future(message = "{validation.fecha-limite-futura}")
        LocalDateTime fechaLimite,

        String descripcion,
        Estado estado
) {
}
