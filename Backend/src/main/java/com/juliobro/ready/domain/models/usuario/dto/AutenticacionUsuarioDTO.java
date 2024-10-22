package com.juliobro.ready.domain.models.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record AutenticacionUsuarioDTO(

        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
