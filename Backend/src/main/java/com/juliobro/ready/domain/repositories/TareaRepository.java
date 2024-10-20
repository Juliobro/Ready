package com.juliobro.ready.domain.repositories;

import com.juliobro.ready.domain.models.tarea.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {}
