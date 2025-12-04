package com.agendadakota.agendadakota.service;

import com.agendadakota.agendadakota.entities.Jornada;
import java.util.List;

public interface JornadaService {

    Jornada crear(Jornada jornada);

    Jornada obtenerPorId(Long id);

    List<Jornada> listar();

    Jornada actualizar(Long id, Jornada jornadaActualizada);

    void eliminar(Long id);
}
