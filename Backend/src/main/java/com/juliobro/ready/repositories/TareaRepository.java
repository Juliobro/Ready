package com.juliobro.ready.repositories;

import com.juliobro.ready.models.tarea.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {}
