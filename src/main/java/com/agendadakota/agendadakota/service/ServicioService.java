package com.agendadakota.agendadakota.service;

import com.agendadakota.agendadakota.dto.ServicioDTO;

import java.util.List;

public interface ServicioService {
    ServicioDTO crear(ServicioDTO dto);
    ServicioDTO obtener(Long id);
    List<ServicioDTO> listar();
    ServicioDTO actualizar(Long id, ServicioDTO dto);
    void eliminar(Long id);
}
