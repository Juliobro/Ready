package com.juliobro.ready.domain.models.tarea;

import com.juliobro.ready.domain.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.RegistroTareaDTO;
import com.juliobro.ready.domain.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /* ------------------------------ Métodos ------------------------------ */

    public Tarea(RegistroTareaDTO datos) {
        this.titulo = datos.titulo();
        this.descripcion = datos.descripcion();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaLimite = datos.fechaLimite();
        this.estado = Estado.PENDIENTE;
    }

    public void actualizarDatos(ActualizarTareaDTO datos) {
        this.titulo = datos.titulo() != null
                ? datos.titulo()
                : this.titulo;
        this.descripcion = datos.descripcion() != null
                ? datos.descripcion()
                : this.descripcion;
        this.fechaLimite = datos.fechaLimite() != null
                ? datos.fechaLimite()
                : this.fechaLimite;
        this.estado = datos.estado() != null
                ? datos.estado()
                : this.estado;
    }
}
