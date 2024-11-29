package com.juliobro.ready.controllers;

import com.juliobro.ready.domain.models.usuario.Usuario;
import com.juliobro.ready.infra.controllers.TareaController;
import com.juliobro.ready.domain.models.tarea.dto.ActualizarTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.DetallesTareaDTO;
import com.juliobro.ready.domain.models.tarea.dto.ListadoTareasDTO;
import com.juliobro.ready.domain.models.tarea.dto.RegistroTareaDTO;
import com.juliobro.ready.domain.models.tarea.Estado;
import com.juliobro.ready.domain.services.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
                LocalDateTime.now().plusDays(1)); // Fecha límite futura

        Long usuarioIdSimulado = 1L; // Simular el ID generado
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");

        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(usuarioIdSimulado, "Título de tarea", "Descripción de tarea",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.PENDIENTE);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        // -- When
        when(tareaService.crearTarea(any(RegistroTareaDTO.class), any(Usuario.class))).thenReturn(detallesTareaDTO);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        ResponseEntity<DetallesTareaDTO> response = tareaController.crearTarea(registroTareaDTO, uriComponentsBuilder, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).crearTarea(any(RegistroTareaDTO.class), eq(usuarioMock));
    }

    @Test
    void listarTareas_DeberiaRetornarListaDeTareas() {
        // -- Given
        Pageable pageable = Pageable.unpaged();
        Page<ListadoTareasDTO> page = new PageImpl<>(Collections.emptyList());

        // Simulando el usuario autenticado
        Long usuarioIdSimulado = 1L;
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");

        when(tareaService.listarTareasPorUsuario(eq(usuarioMock), eq(pageable))).thenReturn(page);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        // -- When
        ResponseEntity<Page<ListadoTareasDTO>> response = tareaController.listarTareas(pageable, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(page);
        verify(tareaService).listarTareasPorUsuario(eq(usuarioMock), eq(pageable));
    }

    @Test
    void actualizarTarea_DeberiaActualizarTareaYRetornarDetalles() {
        // -- Given
        Long idTarea = 1L; // El ID de la tarea
        ActualizarTareaDTO actualizarTareaDTO = new ActualizarTareaDTO("Título actualizado",
                "Descripción actualizada", LocalDateTime.now().plusDays(1), Estado.EN_PROCESO);
        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(idTarea, "Título actualizado",
                "Descripción actualizada", LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.EN_PROCESO);

        Long usuarioIdSimulado = 1L;
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");

        // Simulamos la respuesta del servicio
        when(tareaService.actualizarTarea(eq(idTarea), any(ActualizarTareaDTO.class), eq(usuarioMock)))
                .thenReturn(detallesTareaDTO);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        // -- When
        ResponseEntity<DetallesTareaDTO> response = tareaController.actualizarTarea(actualizarTareaDTO, idTarea, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).actualizarTarea(eq(idTarea), any(ActualizarTareaDTO.class), eq(usuarioMock));
    }

    @Test
    void eliminarTarea_DeberiaEliminarTareaYRetornarNoContent() {
        // -- Given
        Long tareaId = 1L;
        Long usuarioIdSimulado = 1L;
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");

        when(tareaService.eliminarTarea(eq(tareaId), eq(usuarioMock))).thenReturn(true);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        // -- When
        ResponseEntity<Void> response = tareaController.eliminarTarea(tareaId, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(tareaService).eliminarTarea(tareaId, usuarioMock);
    }

    @Test
    void eliminarTarea_DeberiaRetornarNotFoundCuandoNoSeEncuentraTarea() {
        // -- Given
        Long tareaId = 1L;
        Long usuarioIdSimulado = 1L;
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");

        when(tareaService.eliminarTarea(eq(tareaId), eq(usuarioMock))).thenReturn(false);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        // -- When
        ResponseEntity<Void> response = tareaController.eliminarTarea(tareaId, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        verify(tareaService).eliminarTarea(tareaId, usuarioMock);
    }

    @Test
    void mostrarTarea_DeberiaRetornarDetallesDeLaTarea() {
        // -- Given
        Long tareaId = 1L;
        DetallesTareaDTO detallesTareaDTO = new DetallesTareaDTO(1L, "Título de tarea",
                "Descripción de tarea", LocalDateTime.now(), LocalDateTime.now().plusDays(1), Estado.PENDIENTE);

        Long usuarioIdSimulado = 1L;
        Usuario usuarioMock = new Usuario(usuarioIdSimulado, List.of(), "test@example.com", "testuser", "password");
        when(tareaService.mostrarTarea(eq(tareaId), eq(usuarioMock))).thenReturn(detallesTareaDTO);

        // Simular que el usuario fue autenticado y está disponible
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioMock, null, usuarioMock.getAuthorities())
        );

        // -- When
        ResponseEntity<DetallesTareaDTO> response = tareaController.mostrarTarea(tareaId, usuarioMock);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(detallesTareaDTO);
        verify(tareaService).mostrarTarea(tareaId, usuarioMock);
    }
}
