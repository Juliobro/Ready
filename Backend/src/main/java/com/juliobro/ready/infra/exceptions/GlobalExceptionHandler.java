package com.juliobro.ready.infra.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Este handler es para evitar el error 500 cuando no encuentra una entidad en la BD. Se redirige a un 404 NotFound
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarError404() {
        return ResponseEntity.notFound().build();
    }

    //Este handler es para facilitar la lectura de los mensajes de error ocasionados por Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidacionErroresDTO>> tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(ValidacionErroresDTO::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    //Este handler es para tratar los errores en cuanto al formato de los datos ingresados
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {

        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            Throwable causaRaiz = invalidFormatException.getCause();

            if (causaRaiz instanceof DateTimeParseException) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: El formato de la fecha es incorrecto. Asegúrate de usar el formato yyyy-MM-dd'T'HH:mm:ss.");
            }
        }

        if (e.getCause() instanceof ValueInstantiationException valueInstantiationException) {
            return ResponseEntity
                    .badRequest()
                    .body(valueInstantiationException.getCause().toString());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("El formato del cuerpo de la solicitud es incorrecto. Error:\n" + e.getMessage());
    }

    //Este handler es para tratar errores a la hora de haber estructurado el JSON como tal
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<String> handleJsonMappingException() {
        return ResponseEntity
                .badRequest()
                .body("Error: JSON mal formado o estructura no válida.");
    }

    //Este handler es para dar información de errores a la hora de validar reglas de negocio
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> tratarValidacionesDeNegocio(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public record ValidacionErroresDTO(String campo, String error) {
        public ValidacionErroresDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
