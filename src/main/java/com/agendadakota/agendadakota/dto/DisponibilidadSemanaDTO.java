package com.agendadakota.agendadakota.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DisponibilidadSemanaDTO {
    private LocalDate inicioSemana;
    private LocalDate finSemana;
    private List<DisponibilidadDiaDTO> dias;
}

