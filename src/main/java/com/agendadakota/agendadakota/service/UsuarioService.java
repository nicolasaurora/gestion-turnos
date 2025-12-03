package com.agendadakota.agendadakota.service;

import com.agendadakota.agendadakota.dto.UsuarioDTO;
import com.agendadakota.agendadakota.entities.Rol;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crear(UsuarioDTO dto);
    UsuarioDTO actualizar(Long id, UsuarioDTO dto);
    UsuarioDTO obtener(Long id);
    List<UsuarioDTO> listarPorRol(Rol rol);
    List<UsuarioDTO> listarTodos();
    void eliminar(Long id);
}
