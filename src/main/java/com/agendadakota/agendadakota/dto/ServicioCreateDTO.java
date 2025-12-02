package com.agendadakota.agendadakota.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioCreateDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer duracionMinutos;
}
