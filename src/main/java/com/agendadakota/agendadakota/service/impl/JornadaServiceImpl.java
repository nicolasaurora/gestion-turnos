package com.agendadakota.agendadakota.service.impl;

import com.agendadakota.agendadakota.entities.Jornada;
import com.agendadakota.agendadakota.repository.JornadaRepository;
import com.agendadakota.agendadakota.service.JornadaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JornadaServiceImpl implements JornadaService {

    private final JornadaRepository jornadaRepository;

    @Override
    public Jornada crear(Jornada jornada) {
        validarJornada(jornada);
        return jornadaRepository.save(jornada);
    }

    @Override
    public Jornada obtenerPorId(Long id) {
        return jornadaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jornada no encontrada: " + id));
    }

    @Override
    public List<Jornada> listar() {
        return jornadaRepository.findAll();
    }

    @Override
    public Jornada actualizar(Long id, Jornada jornadaActualizada) {
        Jornada existente = obtenerPorId(id);

        existente.setNombre(jornadaActualizada.getNombre());
        existente.setFecha(jornadaActualizada.getFecha());
        existente.setHoraInicio(jornadaActualizada.getHoraInicio());
        existente.setHoraFin(jornadaActualizada.getHoraFin());
        existente.setServicio(jornadaActualizada.getServicio());
        existente.setDescripcion(jornadaActualizada.getDescripcion());
        existente.setProfesional(jornadaActualizada.getProfesional());

        validarJornada(existente);

        return jornadaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!jornadaRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe la jornada con id: " + id);
        }
        jornadaRepository.deleteById(id);
    }

    private void validarJornada(Jornada j) {
        if (j.getHoraFin().isBefore(j.getHoraInicio())) {
            throw new IllegalArgumentException("La hora de fin no puede ser antes que la hora de inicio");
        }
    }
}
