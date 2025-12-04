package com.agendadakota.agendadakota.service.impl;


import com.agendadakota.agendadakota.dto.TurnoDTO;
import com.agendadakota.agendadakota.entities.EstadoTurno;
import com.agendadakota.agendadakota.entities.Turno;
import com.agendadakota.agendadakota.repository.JornadaRepository;
import com.agendadakota.agendadakota.repository.ServicioRepository;
import com.agendadakota.agendadakota.repository.TurnoRepository;
import com.agendadakota.agendadakota.repository.UsuarioRepository;
import com.agendadakota.agendadakota.service.TurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final JornadaRepository jornadaRepository;
    private final ModelMapper mapper;


    public TurnoServiceImpl(TurnoRepository turnoRepository, UsuarioRepository usuarioRepository, ServicioRepository servicioRepository, JornadaRepository jornadaRepository, ModelMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.jornadaRepository = jornadaRepository;
        this.mapper = mapper;
    }

    @Override
    public TurnoDTO crear(TurnoDTO dto) {

        Turno entidad = new Turno();
        entidad.setFechaHoraInicio(dto.getFechaHoraInicio());
        entidad.setFechaHoraFin(dto.getFechaHoraFin());
        entidad.setDuracionMinutos(dto.getDuracionMinutos());
        entidad.setEstado(EstadoTurno.DISPONIBLE);

        // Relación servicio
        if (dto.getServicioId() != null) {
            var servicio = servicioRepository.findById(dto.getServicioId())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            entidad.setServicio(servicio);
        }

        // Relación jornada
        if (dto.getJornadaId() != null) {
            var jornada = jornadaRepository.findById(dto.getJornadaId())
                    .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));
            entidad.setJornada(jornada);
        }

        entidad = turnoRepository.save(entidad);
        return mapper.map(entidad, TurnoDTO.class);
    }

    @Override
    public TurnoDTO obtener(Long id) {
        var entidad = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        return mapper.map(entidad, TurnoDTO.class);
    }

    @Override
    public List<TurnoDTO> listarTodos() {
        return turnoRepository.findAll().stream()
                .map(t -> mapper.map(t, TurnoDTO.class))
                .toList();
    }

    @Override
    public TurnoDTO actualizar(Long id, TurnoDTO dto) {
        var turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        turno.setFechaHoraInicio(dto.getFechaHoraInicio());
        turno.setFechaHoraFin(dto.getFechaHoraFin());
        turno.setDuracionMinutos(dto.getDuracionMinutos());

        turnoRepository.save(turno);
        return mapper.map(turno, TurnoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        var turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        turnoRepository.delete(turno);
    }

    @Override
    public TurnoDTO reservar(Long turnoId, Long pacienteId) {
        var turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        if (turno.getEstado() != EstadoTurno.DISPONIBLE) {
            throw new RuntimeException("El turno no está disponible para reservar");
        }

        var paciente = usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        turno.setPaciente(paciente);
        turno.setEstado(EstadoTurno.RESERVADO);

        turnoRepository.save(turno);

        return mapper.map(turno, TurnoDTO.class);
    }

    @Override
    public TurnoDTO cancelar(Long turnoId) {
        var turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        turno.setPaciente(null);
        turno.setEstado(EstadoTurno.DISPONIBLE);

        turnoRepository.save(turno);

        return mapper.map(turno, TurnoDTO.class);
    }
}
