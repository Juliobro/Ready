package com.juliobro.ready.infra.controllers;

import com.juliobro.ready.domain.services.TareaService;
import com.juliobro.ready.domain.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.domain.models.tarea.dto.RegistroTareaDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService service) {
        this.tareaService = service;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<DetallesTareaDTO> crearTarea(@RequestBody @Valid RegistroTareaDTO datosTarea,
                                                       UriComponentsBuilder ucb) {
        DetallesTareaDTO nuevaTarea = tareaService.crearTarea(datosTarea);
        URI url = ucb.path("/tareas/{id}").buildAndExpand(nuevaTarea.id()).toUri();
        return ResponseEntity.created(url).body(nuevaTarea);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTareasDTO>> listarTareas(@PageableDefault(size = 3) Pageable paginacion) {
        return ResponseEntity.ok(tareaService.listarTareas(paginacion));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetallesTareaDTO> actualizarTarea(@RequestBody @Valid ActualizarTareaDTO datosTarea) {
        DetallesTareaDTO tareaActualizada = tareaService.actualizarTarea(datosTarea);
        return ResponseEntity.ok(tareaActualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        boolean tareaEliminada = tareaService.eliminarTarea(id);
        if (!tareaEliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesTareaDTO> mostrarTarea(@PathVariable Long id) {
        DetallesTareaDTO tarea = tareaService.mostrarTarea(id);
        return ResponseEntity.ok(tarea);
    }
}
