package com.agendadakota.agendadakota.service;

import com.agendadakota.agendadakota.dto.DisponibilidadDiaDTO;
import com.agendadakota.agendadakota.dto.DisponibilidadSemanaDTO;
import com.agendadakota.agendadakota.dto.TurnoDTO;

import java.util.List;

public interface TurnoService {

    TurnoDTO crear(TurnoDTO dto);
    TurnoDTO obtener(Long id);
    List<TurnoDTO> listarTodos();
    TurnoDTO actualizar(Long id, TurnoDTO dto);
    void eliminar(Long id);

    // Logica de negocio
    TurnoDTO reservar(Long turnoId);
    TurnoDTO cancelar(Long turnoId);

    DisponibilidadDiaDTO obtenerDisponibilidadPorDia(String fechaStr);
    DisponibilidadSemanaDTO obtenerDisponibilidadSemana(String fechaInicio);

}
