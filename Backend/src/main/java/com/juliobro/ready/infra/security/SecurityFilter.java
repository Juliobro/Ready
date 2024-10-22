package com.juliobro.ready.infra.security;

import com.juliobro.ready.domain.repositories.UsuarioRepository;
import com.juliobro.ready.infra.security.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.usuarioRepository = repository;
    }

    //Este filtro básicamente analiza e identifica el JWT proporcionado para dar acceso a los demás endpoints
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            String subject = tokenService.getSubject(token);

            if (subject != null) { //Token válido
                var usuario = usuarioRepository.findByEmailOrUsername(subject, subject)
                        .orElseThrow(() -> new UsernameNotFoundException("Este usuario no se encuentra en la BD"));
                var auth = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); //Forzamos un inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(auth); //Para settear manualmente la autenticacion
            }
        }
        filterChain.doFilter(request, response);
    }
}
