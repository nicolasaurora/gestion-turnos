package com.agendadakota.agendadakota.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoCreateDTO {

    private Long pacienteId;
    private Long servicioId;
    private Long jornadaId;
    private LocalDateTime fechaHora;
}
