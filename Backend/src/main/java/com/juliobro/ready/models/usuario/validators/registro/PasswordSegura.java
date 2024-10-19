package com.juliobro.ready.models.usuario.validators.registro;

import com.juliobro.ready.models.usuario.dto.RegistroUsuarioDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordSegura implements RegistrarUsuarioValidator {
    //Esta patrón obliga que la contraseña cumpla con lo que se describe en el mensaje de ValidationException
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d).+$"
    );

    public void validar(RegistroUsuarioDTO datos) {
        String password = datos.password();

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(
                    "La contraseña debe contener al menos una letra y un número");
        }
    }
}
