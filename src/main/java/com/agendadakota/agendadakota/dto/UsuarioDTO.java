package com.agendadakota.agendadakota.dto;

import com.agendadakota.agendadakota.entities.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String telefono;
    private Rol rol;


}
