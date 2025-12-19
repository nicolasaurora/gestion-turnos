package com.agendadakota.agendadakota.dto.auth;

import com.agendadakota.agendadakota.entities.Rol;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private Rol rol;
}
