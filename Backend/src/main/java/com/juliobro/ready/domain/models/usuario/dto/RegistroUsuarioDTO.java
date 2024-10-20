package com.juliobro.ready.domain.models.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
        @NotBlank(message = "{validation.username}")
        @Size(min = 4, message = "{validation.username.size}")
        String username,

        @NotBlank(message = "{validation.email}")
        @Email(message = "{validation.email.valid}")
        String email,

        @NotBlank(message = "{validation.userpass}")
        @Size(min = 6, message = "{validation.userpass.size}")
        String password
) {
}
