package com.agendadakota.agendadakota.repository;

import com.agendadakota.agendadakota.entities.Jornada;
import com.agendadakota.agendadakota.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    Optional<Jornada> findByFecha(LocalDate fecha);
}
