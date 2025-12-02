package com.agendadakota.agendadakota.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jornadas")
public class Jornada {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // ej "Jornada de depilacion definitiva" (tipo/etiqueta)
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio; // la jornada está pensada para ese servicio (o podés permitir null y admitir varios servicios por jornada con otra entidad)

    private String descripcion; // opcional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private Usuario profesional;

    @OneToMany(mappedBy = "jornada")
    private Set<Turno> turnos;
}
