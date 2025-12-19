package com.agendadakota.agendadakota.repository;

import com.agendadakota.agendadakota.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByJornadaId(Long jornadaId);

    List<Turno> findByJornada_Id(Long id);
}
