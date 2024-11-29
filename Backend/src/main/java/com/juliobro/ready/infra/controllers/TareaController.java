package com.juliobro.ready.infra.controllers;

import com.juliobro.ready.domain.models.usuario.Usuario;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                                       UriComponentsBuilder ucb,
                                                       @AuthenticationPrincipal Usuario usuario) {
        DetallesTareaDTO nuevaTarea = tareaService.crearTarea(datosTarea, usuario);
        URI url = ucb.path("/tareas/{id}").buildAndExpand(nuevaTarea.id()).toUri();
        return ResponseEntity.created(url).body(nuevaTarea);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTareasDTO>> listarTareas(@PageableDefault(size = 15) Pageable paginacion,
                                                               @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(tareaService.listarTareasPorUsuario(usuario, paginacion));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetallesTareaDTO> actualizarTarea(@RequestBody @Valid ActualizarTareaDTO datosTarea,
                                                            @PathVariable Long id,
                                                            @AuthenticationPrincipal Usuario usuario) {
        DetallesTareaDTO tareaActualizada = tareaService.actualizarTarea(id, datosTarea, usuario);
        return ResponseEntity.ok(tareaActualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id,
                                              @AuthenticationPrincipal Usuario usuario) {
        boolean tareaEliminada = tareaService.eliminarTarea(id, usuario);
        if (!tareaEliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallesTareaDTO> mostrarTarea(@PathVariable Long id,
                                                         @AuthenticationPrincipal Usuario usuario) {
        DetallesTareaDTO tarea = tareaService.mostrarTarea(id, usuario);
        return ResponseEntity.ok(tarea);
    }
}
