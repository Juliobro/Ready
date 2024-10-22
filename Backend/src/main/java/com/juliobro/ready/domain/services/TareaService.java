package com.juliobro.ready.domain.services;

import com.juliobro.ready.domain.models.tarea.Tarea;
import com.juliobro.ready.domain.models.usuario.Usuario;
import com.juliobro.ready.domain.repositories.TareaRepository;
import com.juliobro.ready.domain.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.domain.models.tarea.dto.RegistroTareaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }


    public DetallesTareaDTO crearTarea(RegistroTareaDTO datosTarea, Usuario usuario) {
        Tarea tarea = new Tarea(datosTarea);
        tarea.setUsuario(usuario);
        tarea = tareaRepository.save(tarea);
        return new DetallesTareaDTO(tarea);
    }


    public Page<ListadoTareasDTO> listarTareasPorUsuario(Usuario usuario, Pageable paginacion) {
        return tareaRepository.findByUsuario(usuario, paginacion).map(ListadoTareasDTO::new);
    }


    public DetallesTareaDTO actualizarTarea(ActualizarTareaDTO datosTarea, Usuario usuario) {
        Tarea tarea = tareaRepository.getReferenceById(datosTarea.id());
        if (!tarea.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("No tienes permiso para actualizar esta tarea");
        }

        tarea.actualizarDatos(datosTarea);
        return new DetallesTareaDTO(tarea);
    }


    public boolean eliminarTarea(Long id, Usuario usuario) {
        Tarea tarea = tareaRepository.getReferenceById(id);
        if (!tarea.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("No tienes permiso para eliminar esta tarea");
        }

        if (!tareaRepository.existsById(id)) {
            return false;
        }
        tareaRepository.deleteById(id);
        return true;
    }


    public DetallesTareaDTO mostrarTarea(Long id, Usuario usuario) {
        Tarea tarea = tareaRepository.getReferenceById(id);
        if (!tarea.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("No tienes permiso para ver esta tarea");
        }

        return new DetallesTareaDTO(tarea);
    }
}
