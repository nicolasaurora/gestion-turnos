package com.agendadakota.agendadakota.dto;

import com.agendadakota.agendadakota.entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {

    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private Rol rol;

}
