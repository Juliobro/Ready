package com.juliobro.ready.domain.models.tarea.dto;

import com.juliobro.ready.domain.models.tarea.Estado;
import com.juliobro.ready.domain.models.tarea.Tarea;

import java.time.LocalDateTime;

public record ListadoTareasDTO(
        Long id,
        String titulo,
        String descripcion,
        LocalDateTime fechaLimite,
        Estado estado
) {
    public ListadoTareasDTO(Tarea tarea) {
        this(tarea.getId(), tarea.getTitulo(), tarea.getDescripcion(),
                tarea.getFechaLimite(), tarea.getEstado());
    }
}
