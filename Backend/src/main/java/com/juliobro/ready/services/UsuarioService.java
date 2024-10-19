package com.juliobro.ready.services;

import com.juliobro.ready.models.usuario.Usuario;
import com.juliobro.ready.models.usuario.dto.RegistroUsuarioDTO;
import com.juliobro.ready.models.usuario.validators.registro.RegistrarUsuarioValidator;
import com.juliobro.ready.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<RegistrarUsuarioValidator> validadoresRegistro;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          List<RegistrarUsuarioValidator> validadoresRegistro) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.validadoresRegistro = validadoresRegistro;
    }


    public Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro) {

        validadoresRegistro.forEach(v -> v.validar(datosRegistro));
        String passwordEncriptada = passwordEncoder.encode(datosRegistro.password());
        Usuario nuevoUsuario = new Usuario(
                null, null, datosRegistro.email(), datosRegistro.username(), passwordEncriptada);

        return usuarioRepository.save(nuevoUsuario);
    }
}
