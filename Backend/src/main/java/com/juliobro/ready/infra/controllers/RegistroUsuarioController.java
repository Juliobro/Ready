package com.juliobro.ready.infra.controllers;

import com.juliobro.ready.domain.models.usuario.Usuario;
import com.juliobro.ready.domain.models.usuario.dto.RegistroUsuarioDTO;
import com.juliobro.ready.domain.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class RegistroUsuarioController {

    private final UsuarioService usuarioService;

    public RegistroUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO datosRegistro){
        Usuario nuevoUsuario = usuarioService.registrarUsuario(datosRegistro);
        return ResponseEntity.ok("Usuario registrado exitosamente con ID: " + nuevoUsuario.getId());
    }
}
