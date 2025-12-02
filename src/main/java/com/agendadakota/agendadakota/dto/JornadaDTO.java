package com.agendadakota.agendadakota.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaDTO {
    private Long id;
    private Long profesionalId;
    private String profesionalNombre;
    private Long servicioId;
    private String servicioNombre;
    private String nombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
