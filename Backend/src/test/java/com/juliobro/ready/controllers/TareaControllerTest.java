package com.juliobro.ready.controllers;

import com.juliobro.ready.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.models.tarea.dto.RegistroTareaDTO;
import com.juliobro.ready.models.tarea.Estado;
import com.juliobro.ready.services.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TareaControllerTest {

    @InjectMocks
    private TareaController tareaController;

    @Mock
    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearTarea_DeberiaCrearTareaYRetornarDetalles() {
        // -- Given
        RegistroTareaDTO registroTareaDTO = new RegistroTareaDTO("Título de tarea", "Descripción de tarea",
                LocalDateTime.now().plusDays(1)); //Fecha límite futura
        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(1L, "Título de tarea", "Descripción de tarea",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.PENDIENTE);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();


        // -- When
        when(tareaService.crearTarea(any(RegistroTareaDTO.class))).thenReturn(detallesTareaDTO);
        ResponseEntity<DetallesTareaDTO> response = tareaController.crearTarea(registroTareaDTO, uriComponentsBuilder);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).crearTarea(any(RegistroTareaDTO.class));
        verify(tareaService, times(1)).crearTarea(registroTareaDTO);
    }

    @Test
    void listarTareas_DeberiaRetornarListaDeTareas() {
        // -- Given
        Pageable pageable = Pageable.unpaged();
        Page<ListadoTareasDTO> page = new PageImpl<>(Collections.emptyList());
        when(tareaService.listarTareas(pageable)).thenReturn(page);

        // -- When
        ResponseEntity<Page<ListadoTareasDTO>> response = tareaController.listarTareas(pageable);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(page);
        verify(tareaService).listarTareas(pageable);
    }

    @Test
    void actualizarTarea_DeberiaActualizarTareaYRetornarDetalles() {
        // -- Given
        ActualizarTareaDTO actualizarTareaDTO = new ActualizarTareaDTO(1L, "Título actualizado",
                "Descripción actualizada", LocalDateTime.now().plusDays(1), Estado.EN_PROCESO);
        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(1L, "Título actualizado",
                "Descripción actualizada", LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.EN_PROCESO);

        when(tareaService.actualizarTarea(any(ActualizarTareaDTO.class))).thenReturn(detallesTareaDTO);

        // -- When
        ResponseEntity<DetallesTareaDTO> response = tareaController.actualizarTarea(actualizarTareaDTO);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).actualizarTarea(actualizarTareaDTO);
    }

    @Test
    void eliminarTarea_DeberiaEliminarTareaYRetornarNoContent() {
        // -- Given
        Long tareaId = 1L;
        when(tareaService.eliminarTarea(tareaId)).thenReturn(true);

        // -- When
        ResponseEntity<Void> response = tareaController.eliminarTarea(tareaId);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(tareaService).eliminarTarea(tareaId);
    }

    @Test
    void eliminarTarea_DeberiaRetornarNotFoundCuandoNoSeEncuentraTarea() {
        // -- Given
        Long tareaId = 1L;
        when(tareaService.eliminarTarea(tareaId)).thenReturn(false);

        // -- When
        ResponseEntity<Void> response = tareaController.eliminarTarea(tareaId);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        verify(tareaService).eliminarTarea(tareaId);
    }

    @Test
    void mostrarTarea_DeberiaRetornarDetallesDeLaTarea() {
        // -- Given
        Long tareaId = 1L;
        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(1L, "Título de tarea",
                "Descripción de tarea", LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.PENDIENTE);
        when(tareaService.mostrarTarea(tareaId)).thenReturn(detallesTareaDTO);

        // -- When
        ResponseEntity<DetallesTareaDTO> response = tareaController.mostrarTarea(tareaId);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).mostrarTarea(tareaId);
    }
}
