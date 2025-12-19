package com.agendadakota.agendadakota.controller;

import com.agendadakota.agendadakota.dto.DisponibilidadDiaDTO;
import com.agendadakota.agendadakota.dto.DisponibilidadSemanaDTO;
import com.agendadakota.agendadakota.dto.TurnoDTO;
import com.agendadakota.agendadakota.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agenda/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PreAuthorize("hasRole('ADMIN', 'PACIENTE', 'PROFESIONAL)")
    @PostMapping
    public ResponseEntity<TurnoDTO> crear(@RequestBody TurnoDTO dto) {
        var creado = turnoService.crear(dto);
        return ResponseEntity.created(URI.create("/agenda/turnos/" + creado.getId()))
                .body(creado);
    }

    @PreAuthorize("hasRole('ADMIN', 'PACIENTE', 'PROFESIONAL)")
    @GetMapping("/disponibilidad")
    public ResponseEntity<DisponibilidadDiaDTO> disponibilidadPorDia(
            @RequestParam String fecha
    ) {
        return ResponseEntity.ok(turnoService.obtenerDisponibilidadPorDia(fecha));
    }

    @PreAuthorize("hasRole('ADMIN', 'PACIENTE', 'PROFESIONAL)")
    @GetMapping("/disponibilidad/semana")
    public ResponseEntity<DisponibilidadSemanaDTO> disponibilidadSemana(
            @RequestParam(required = false) String inicio // formato: yyyy-MM-dd
    ) {
        return ResponseEntity.ok(turnoService.obtenerDisponibilidadSemana(inicio));
    }

    @PreAuthorize("hasRole('ADMIN','PROFESIONAL)")
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtener(id));
    }

    @PreAuthorize("hasRole('ADMIN','PROFESIONAL)")
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @PreAuthorize("hasRole('ADMIN','PROFESIONAL)")
    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> actualizar(@PathVariable Long id, @RequestBody TurnoDTO dto) {
        return ResponseEntity.ok(turnoService.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN','PROFESIONAL)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN','PACIENTE','PROFESIONAL)")
    @PostMapping("/{turnoId}/reservar")
    public ResponseEntity<TurnoDTO> reservar(@PathVariable Long turnoId) {
        return ResponseEntity.ok(turnoService.reservar(turnoId));
    }

    @PreAuthorize("hasRole('ADMIN','PACIENTE','PROFESIONAL)")
    @PostMapping("/{turnoId}/cancelar")
    public ResponseEntity<TurnoDTO> cancelar(@PathVariable Long turnoId) {
        return ResponseEntity.ok(turnoService.cancelar(turnoId));
    }
}
