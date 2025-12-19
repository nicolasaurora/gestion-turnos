package com.agendadakota.agendadakota.controller;

import com.agendadakota.agendadakota.dto.UsuarioDTO;
import com.agendadakota.agendadakota.dto.auth.RegisterRequest;
import com.agendadakota.agendadakota.entities.Usuario;
import com.agendadakota.agendadakota.exception.ValidacionException;
import com.agendadakota.agendadakota.repository.UsuarioRepository;
import com.agendadakota.agendadakota.security.JwtService;
import com.agendadakota.agendadakota.security.dto.LoginRequest;
import com.agendadakota.agendadakota.security.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        var usuario = usuarioRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(req.password(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name());

        return new LoginResponse(token, usuario.getRol().name());
    }


    @PostMapping("/register")
    public UsuarioDTO register(@RequestBody RegisterRequest req) {

        // email único
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ValidacionException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(req.getEmail());
        usuario.setPassword(encoder.encode(req.getPassword()));
        usuario.setNombre(req.getNombre());
        usuario.setApellido(req.getApellido());
        usuario.setTelefono(req.getTelefono());
        usuario.setRol(req.getRol());

        usuarioRepository.save(usuario);

        return mapper.map(usuario, UsuarioDTO.class);
    }
}

