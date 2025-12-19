package com.agendadakota.agendadakota.security;

import com.agendadakota.agendadakota.entities.Usuario;
import com.agendadakota.agendadakota.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UsuarioRepository usuarioRepo;

    public Usuario getUsuarioActual() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en contexto"));
    }
}

