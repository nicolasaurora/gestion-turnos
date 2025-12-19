package com.agendadakota.agendadakota.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DisponibilidadDiaDTO {

    private LocalDate fecha;
    private List<HorarioDisponibleDTO> horarios;

    @Data
    public static class HorarioDisponibleDTO {
        private String hora;
        private boolean disponible;

        public HorarioDisponibleDTO(String hora, boolean disponible) {
            this.hora = hora;
            this.disponible = disponible;
        }
    }
}
