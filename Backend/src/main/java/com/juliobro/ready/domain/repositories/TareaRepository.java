package com.juliobro.ready.domain.repositories;

import com.juliobro.ready.domain.models.tarea.Tarea;
import com.juliobro.ready.domain.models.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    Page<Tarea> findByUsuario(Usuario usuario, Pageable pageable);
}
