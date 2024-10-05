package com.juliobro.ready.models.tarea.dto;

import com.juliobro.ready.models.tarea.Estado;
import com.juliobro.ready.models.tarea.Tarea;

import java.time.LocalDateTime;

public record ListadoTareasDTO(
        String titulo,
        String descripcion,
        LocalDateTime fechaLimite,
        Estado estado
) {
    public ListadoTareasDTO(Tarea tarea) {
        this(tarea.getTitulo(), tarea.getDescripcion(),
                tarea.getFechaLimite(), tarea.getEstado());
    }
}
