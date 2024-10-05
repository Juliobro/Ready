package com.juliobro.ready.services;

import com.juliobro.ready.models.tarea.Tarea;
import com.juliobro.ready.repositories.TareaRepository;
import com.juliobro.ready.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.models.tarea.dto.RegistroTareaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }


    public DetallesTareaDTO crearTarea(RegistroTareaDTO datosTarea) {
        Tarea tarea = tareaRepository.save(new Tarea(datosTarea));
        return new DetallesTareaDTO(tarea);
    }

    public Page<ListadoTareasDTO> listarTareas(Pageable paginacion) {
        return tareaRepository.findAll(paginacion).map(ListadoTareasDTO::new);
    }

    public DetallesTareaDTO actualizarTarea(ActualizarTareaDTO datosTarea) {
        Tarea tarea = tareaRepository.getReferenceById(datosTarea.id());
        tarea.actualizarDatos(datosTarea);
        return new DetallesTareaDTO(tarea);
    }

    public boolean eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            return false;
        }
        tareaRepository.deleteById(id);
        return true;
    }

    public DetallesTareaDTO mostrarTarea(Long id) {
        Tarea tarea = tareaRepository.getReferenceById(id);
        return new DetallesTareaDTO(tarea);
    }
}
