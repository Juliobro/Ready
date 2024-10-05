package com.juliobro.ready.controllers;

import com.juliobro.ready.models.tarea.TareaRepository;
import com.juliobro.ready.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.models.tarea.dto.RegistroTareaDTO;
import com.juliobro.ready.models.tarea.Tarea;
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

    @GetMapping
    public ResponseEntity<Page<ListadoTareasDTO>> listarTareas(@PageableDefault(size = 3) Pageable paginacion) {
        return ResponseEntity.ok(tareaRepository.findAll(paginacion).map(ListadoTareasDTO::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetallesTareaDTO> actualizarTarea(@RequestBody @Valid ActualizarTareaDTO datosTarea) {
        Tarea tarea = tareaRepository.getReferenceById(datosTarea.id());
        tarea.actualizarDatos(datosTarea);
        return ResponseEntity.ok(new DetallesTareaDTO(tarea));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        if (!tareaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tareaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesTareaDTO> mostrarTarea(@PathVariable Long id) {
        Tarea tarea = tareaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetallesTareaDTO(tarea));
    }
}
