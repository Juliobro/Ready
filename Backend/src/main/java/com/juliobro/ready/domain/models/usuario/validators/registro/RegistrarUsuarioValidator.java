package com.juliobro.ready.domain.models.usuario.validators.registro;

import com.juliobro.ready.domain.models.usuario.dto.RegistroUsuarioDTO;

public interface RegistrarUsuarioValidator {
    void validar(RegistroUsuarioDTO datos);
}
