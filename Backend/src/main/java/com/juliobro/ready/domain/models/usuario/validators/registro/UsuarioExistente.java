package com.juliobro.ready.domain.models.usuario.validators.registro;

import com.juliobro.ready.domain.models.usuario.dto.RegistroUsuarioDTO;
import com.juliobro.ready.domain.repositories.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UsuarioExistente implements RegistrarUsuarioValidator{

    private final UsuarioRepository usuarioRepository;

    public UsuarioExistente(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(RegistroUsuarioDTO datos) {
        if (usuarioRepository.findByUsername(datos.username()).isPresent()) {
            throw new ValidationException("El nombre de usuario ya está en uso.");
        }
        if (usuarioRepository.findByEmail(datos.email()).isPresent()) {
            throw new ValidationException("El email ya está en uso.");
        }
    }
}
