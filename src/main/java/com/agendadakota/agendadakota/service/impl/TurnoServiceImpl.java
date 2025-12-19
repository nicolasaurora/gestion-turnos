package com.agendadakota.agendadakota.service.impl;


import com.agendadakota.agendadakota.dto.DisponibilidadDiaDTO;
import com.agendadakota.agendadakota.dto.DisponibilidadSemanaDTO;
import com.agendadakota.agendadakota.dto.TurnoDTO;
import com.agendadakota.agendadakota.entities.EstadoTurno;
import com.agendadakota.agendadakota.entities.Jornada;
import com.agendadakota.agendadakota.entities.Turno;
import com.agendadakota.agendadakota.exception.RecursoNoEncontradoException;
import com.agendadakota.agendadakota.exception.ValidacionException;
import com.agendadakota.agendadakota.repository.JornadaRepository;
import com.agendadakota.agendadakota.repository.ServicioRepository;
import com.agendadakota.agendadakota.repository.TurnoRepository;
import com.agendadakota.agendadakota.repository.UsuarioRepository;
import com.agendadakota.agendadakota.security.AuthUtils;
import com.agendadakota.agendadakota.service.TurnoService;
import org.modelmapper.ModelMapper;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final JornadaRepository jornadaRepository;
    private final ModelMapper mapper;
    private final AuthUtils authUtils;



    public TurnoServiceImpl(TurnoRepository turnoRepository, UsuarioRepository usuarioRepository, ServicioRepository servicioRepository, JornadaRepository jornadaRepository, ModelMapper mapper, AuthUtils authUtils) {
        this.turnoRepository = turnoRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.jornadaRepository = jornadaRepository;
        this.mapper = mapper;
        this.authUtils = authUtils;
    }

    private void validarTurno(TurnoDTO dto, Jornada jornada, Long turnoIdExcluir) {

        if (dto.getFechaHoraInicio() == null || dto.getFechaHoraFin() == null) {
            throw new ValidationException("Las fechas del turno son obligatorias");
        }

        if (!dto.getFechaHoraFin().isAfter(dto.getFechaHoraInicio())) {
            throw new ValidationException("La fecha/hora de fin debe ser posterior al inicio");
        }

        if (dto.getDuracionMinutos() == null || dto.getDuracionMinutos() <= 0) {
            throw new ValidationException("La duración del turno debe ser mayor a 0 minutos");
        }

        if (dto.getFechaHoraInicio().isBefore(LocalDateTime.now())) {
            throw new ValidationException("No se pueden crear turnos en el pasado");
        }

        // validar que esté dentro de la jornada
        if (jornada != null) {
            LocalDateTime inicioJornada = jornada.getFecha().atTime(jornada.getHoraInicio());
            LocalDateTime finJornada = jornada.getFecha().atTime(jornada.getHoraFin());

            if (dto.getFechaHoraInicio().isBefore(inicioJornada)
                    || dto.getFechaHoraFin().isAfter(finJornada)) {
                throw new ValidationException("El turno debe estar dentro del horario de la jornada");
            }
            boolean superpone = seSuperponeConOtroTurno(
                    dto.getFechaHoraInicio(),
                    dto.getFechaHoraFin(),
                    jornada.getId(),
                    turnoIdExcluir
            );

            if (superpone) {
                throw new IllegalArgumentException("Ya existe otro turno dentro de este horario");
            }
        }
    }

    @Override
    public TurnoDTO crear(TurnoDTO dto) {

        Jornada jornada = null;

        if (dto.getJornadaId() != null) {
            jornada = jornadaRepository.findById(dto.getJornadaId())
                    .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));
        }

        // Validar con la jornada cargada
        validarTurno(dto, jornada, null);

        Turno entidad = new Turno();
        entidad.setFechaHoraInicio(dto.getFechaHoraInicio());
        entidad.setFechaHoraFin(dto.getFechaHoraFin());
        entidad.setDuracionMinutos(dto.getDuracionMinutos());
        entidad.setEstado(EstadoTurno.DISPONIBLE);

        if (dto.getServicioId() != null) {
            var servicio = servicioRepository.findById(dto.getServicioId())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            entidad.setServicio(servicio);
        }

        if (jornada != null) {
            entidad.setJornada(jornada);
        }

        entidad = turnoRepository.save(entidad);
        return mapper.map(entidad, TurnoDTO.class);
    }

    @Override
    public TurnoDTO obtener(Long id) {
        var entidad = turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));

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
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));

        Jornada jornada = turno.getJornada();

        validarTurno(dto, jornada, id); // <--- llamada con id para NO incluirlo en superposición

        turno.setFechaHoraInicio(dto.getFechaHoraInicio());
        turno.setFechaHoraFin(dto.getFechaHoraFin());
        turno.setDuracionMinutos(dto.getDuracionMinutos());

        turnoRepository.save(turno);
        return mapper.map(turno, TurnoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        var turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));

        turnoRepository.delete(turno);
    }

    @Override
    public TurnoDTO reservar(Long turnoId) {

        var turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));

        if (turno.getEstado() != EstadoTurno.DISPONIBLE) {
            throw new ValidacionException("El turno ya está reservado");
        }

        // OBTENER USUARIO AUTENTICADO DESDE EL JWT
        var paciente = authUtils.getUsuarioActual();

        turno.setPaciente(paciente);
        turno.setEstado(EstadoTurno.RESERVADO);

        turnoRepository.save(turno);

        return mapper.map(turno, TurnoDTO.class);
    }


    @Override
    public TurnoDTO cancelar(Long turnoId) {
        var turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));

        turno.setPaciente(null);
        turno.setEstado(EstadoTurno.DISPONIBLE);

        turnoRepository.save(turno);

        return mapper.map(turno, TurnoDTO.class);
    }

    private boolean seSuperponeConOtroTurno(LocalDateTime inicio, LocalDateTime fin, Long jornadaId, Long turnoIdExcluir) {
        List<Turno> turnosExistentes = turnoRepository.findByJornadaId(jornadaId);

        for (Turno t : turnosExistentes) {

            if (turnoIdExcluir != null && t.getId().equals(turnoIdExcluir)) {
                continue; // ignorar el turno que estoy actualizando
            }
            // si el turno actual está dentro del rango o lo pisa, solapa
            boolean superpone = inicio.isBefore(t.getFechaHoraFin())
                    && fin.isAfter(t.getFechaHoraInicio());

            if (superpone) {
                return true;
            }
        }

        return false;
    }


    @Override
    public DisponibilidadDiaDTO obtenerDisponibilidadPorDia(String fechaStr) {

        LocalDate fecha = LocalDate.parse(fechaStr);

        // Buscar la jornada del día
        Jornada jornada = jornadaRepository
                .findByFecha(fecha)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No hay jornada cargada para el día " + fecha
                ));

        // Horario jornada
        LocalDateTime inicio = jornada.getFecha().atTime(jornada.getHoraInicio());
        LocalDateTime fin = jornada.getFecha().atTime(jornada.getHoraFin());

        // Obtener todos los turnos ya creados para ese día
        List<Turno> turnos = turnoRepository.findByJornada_Id(jornada.getId());

        // Lista de horarios a devolver
        List<DisponibilidadDiaDTO.HorarioDisponibleDTO> listaHorarios = new ArrayList<>();

        // Recorremos todos los horarios en bloques de 30 min
        LocalDateTime actual = inicio;
        while (!actual.isAfter(fin.minusMinutes(30))) {

            LocalDateTime actualFin = actual.plusMinutes(30);

            LocalDateTime finalActual = actual;
            boolean disponible = turnos.stream()
                    .noneMatch(t ->
                            t.getFechaHoraInicio().equals(finalActual) ||
                                    (
                                            t.getFechaHoraInicio().isBefore(actualFin) &&
                                                    t.getFechaHoraFin().isAfter(finalActual)
                                    )
                    );

            listaHorarios.add(
                    new DisponibilidadDiaDTO.HorarioDisponibleDTO(
                            actual.toLocalTime().toString(),
                            disponible
                    )
            );

            actual = actual.plusMinutes(30);
        }

        // DTO final
        DisponibilidadDiaDTO dto = new DisponibilidadDiaDTO();
        dto.setFecha(fecha);
        dto.setHorarios(listaHorarios);

        return dto;
    }


    @Override
    public DisponibilidadSemanaDTO obtenerDisponibilidadSemana(String fechaInicioStr) {
        LocalDate fechaInicio;
        if (fechaInicioStr == null || fechaInicioStr.isBlank()) {
            fechaInicio = LocalDate.now();
        } else {
            fechaInicio = LocalDate.parse(fechaInicioStr); // espera formato ISO: yyyy-MM-dd
        }

        List<DisponibilidadDiaDTO> dias = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate fechaActual = fechaInicio.plusDays(i);
            try {
                // reutiliza el método que ya implementaste
                DisponibilidadDiaDTO dia = obtenerDisponibilidadPorDia(fechaActual.toString());
                dias.add(dia);
            } catch (RecursoNoEncontradoException e) {
                // Si no hay jornada para ese día, devolvemos día vacío (frontend lo muestra como sin horarios)
                DisponibilidadDiaDTO diaSinJornada = new DisponibilidadDiaDTO();
                diaSinJornada.setFecha(fechaActual);
                diaSinJornada.setHorarios(new ArrayList<>());
                dias.add(diaSinJornada);
            }
        }

        DisponibilidadSemanaDTO semanaDTO = new DisponibilidadSemanaDTO();
        semanaDTO.setInicioSemana(fechaInicio);
        semanaDTO.setFinSemana(fechaInicio.plusDays(6));
        semanaDTO.setDias(dias);

        return semanaDTO;
    }


}
