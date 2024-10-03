package com.juliobro.ready.models.tarea;

import com.juliobro.ready.models.tarea.dto.RegistroTareaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLimite;

    public Tarea(RegistroTareaDTO datos) {
        this.titulo = datos.titulo();
        this.descripcion = datos.descripcion();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaLimite = datos.fechaLimite();
        this.estado = Estado.PENDIENTE;
    }
}
