package com.agendadakota.agendadakota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String telefono;

    // Rol: ADMIN, PROFESIONAL, PACIENTE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Relación con Turnos (como paciente)
    @OneToMany(mappedBy = "paciente")
    private Set<Turno> turnosComoPaciente;

    // Relación con Jornadas (como profesional)
    @OneToMany(mappedBy = "profesional")
    private Set<Jornada> jornadas;

}
