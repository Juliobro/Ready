package com.juliobro.ready.domain.models.tarea.dto;

import com.juliobro.ready.domain.models.tarea.Estado;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActualizarTareaDTO(

        @NotNull(message = "{validation.id}")
        Long id,

        String titulo,
        String descripcion,
        LocalDateTime fechaLimite,
        Estado estado
) {
}
