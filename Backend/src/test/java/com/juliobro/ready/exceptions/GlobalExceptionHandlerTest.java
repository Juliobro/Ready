package com.juliobro.ready.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.juliobro.ready.infra.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void tratarError400_DeberiaRetornarBadRequest() {
        // -- Given
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objeto", "campo", "mensaje de error"));
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getFieldErrors()).thenReturn(fieldErrors);

        // -- When
        ResponseEntity<List<GlobalExceptionHandler.ValidacionErroresDTO>> response = exceptionHandler.tratarError400(exception);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().campo()).isEqualTo("campo");
        assertThat(response.getBody().getFirst().error()).isEqualTo("mensaje de error");
    }

    @Test
    void handleHttpMessageNotReadable_DeberiaRetornarBadRequestForInvalidFormat() {
        // -- Given
        InvalidFormatException exception = mock(InvalidFormatException.class);
        when(exception.getCause()).thenReturn(new DateTimeParseException("msg", "input", 0));
        HttpMessageNotReadableException httpException = new HttpMessageNotReadableException("error", exception);

        // -- When
        ResponseEntity<String> response = exceptionHandler.handleHttpMessageNotReadable(httpException);

        // -- Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Error: El formato de la fecha es incorrecto. Asegúrate de usar el formato yyyy-MM-dd'T'HH:mm:ss.");
    }
}
