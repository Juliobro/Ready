package com.juliobro.ready.domain.models.tarea.dto;

import com.juliobro.ready.domain.models.tarea.Estado;
import com.juliobro.ready.domain.models.tarea.Tarea;

import java.time.LocalDateTime;

public record DetallesTareaDTO(
        Long id,
        String titulo,
        String descripcion,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaLimite,
        Estado estado
) {
    public DetallesTareaDTO(Tarea tarea) {
        this(tarea.getId(), tarea.getTitulo(), tarea.getDescripcion(),
                tarea.getFechaCreacion(), tarea.getFechaLimite(), tarea.getEstado());
    }
}
