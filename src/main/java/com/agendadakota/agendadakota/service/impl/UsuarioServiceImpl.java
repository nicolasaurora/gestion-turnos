package com.agendadakota.agendadakota.service.impl;


import com.agendadakota.agendadakota.dto.UsuarioDTO;
import com.agendadakota.agendadakota.entities.Rol;
import com.agendadakota.agendadakota.entities.Usuario;
import com.agendadakota.agendadakota.repository.UsuarioRepository;
import com.agendadakota.agendadakota.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ModelMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }


    @Override
    public UsuarioDTO crear(UsuarioDTO dto) {
        var entidad = mapper.map(dto, Usuario.class);

        if(entidad.getPassword() == null) {
            entidad.setPassword("123456");
        }

        entidad = usuarioRepository.save(entidad);
        return mapper.map(entidad, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        var entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setEmail(dto.getEmail());
        entidad.setTelefono(dto.getTelefono());
        entidad.setRol(dto.getRol());

        usuarioRepository.save(entidad);

        return mapper.map(entidad, UsuarioDTO.class);

    }

    @Override
    public UsuarioDTO obtener(Long id) {
        var entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        return mapper.map(entidad, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> listarPorRol(Rol rol) {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getRol() == rol)
                .map(u -> mapper.map(u, UsuarioDTO.class))
                .toList();
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, UsuarioDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        var entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        usuarioRepository.delete(entidad);

    }
}
