package com.juliobro.ready.controllers;

import com.juliobro.ready.models.tarea.TareaRepository;
import com.juliobro.ready.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.models.tarea.dto.RegistroTareaDTO;
import com.juliobro.ready.models.tarea.Tarea;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaRepository tareaRepository;

    public TareaController(TareaRepository repository) {
        this.tareaRepository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DetallesTareaDTO> crearTarea(@RequestBody @Valid RegistroTareaDTO datosTarea,
                                                       UriComponentsBuilder ucb) {
        Tarea tarea = tareaRepository.save(new Tarea(datosTarea));
        URI url = ucb.path("/tareas/{id}").buildAndExpand(tarea.getId()).toUri();
        return ResponseEntity.created(url).body(new DetallesTareaDTO(tarea));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesTareaDTO> mostrarTarea(@PathVariable Long id) {
        Tarea tarea = tareaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetallesTareaDTO(tarea));
    }
}
