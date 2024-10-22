package com.juliobro.ready.infra.controllers;

import com.juliobro.ready.domain.models.usuario.Usuario;
import com.juliobro.ready.domain.models.usuario.dto.AutenticacionUsuarioDTO;
import com.juliobro.ready.infra.security.jwt.JwtDTO;
import com.juliobro.ready.infra.security.jwt.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager atm, TokenService token) {
        this.authenticationManager = atm;
        this.tokenService = token;
    }

    @PostMapping
    public ResponseEntity<JwtDTO> autenticarUsuario(@RequestBody @Valid AutenticacionUsuarioDTO datosAutenticacion) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacion.login(), datosAutenticacion.password());
        //En este punto, AuthManager intenta autenticar el token utilizando el UserDetailsService proporcionado por
        //mi clase AutenticacionService. AuthManager fue definido en SecurityConfig.
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        String jwt = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JwtDTO(jwt));
    }
}
