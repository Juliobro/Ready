package com.juliobro.ready.domain.models.tarea.dto;

import com.juliobro.ready.domain.models.tarea.Estado;

import java.time.LocalDateTime;

public record ActualizarTareaDTO(
        String titulo,
        String descripcion,
        LocalDateTime fechaLimite,
        Estado estado
) {
}
