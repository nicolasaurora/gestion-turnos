package com.agendadakota.agendadakota.controller;

import com.agendadakota.agendadakota.dto.TurnoDTO;
import com.agendadakota.agendadakota.service.TurnoService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<TurnoDTO> crear(@RequestBody TurnoDTO dto) {
        var creado = turnoService.crear(dto);
        return ResponseEntity.created(URI.create("/agenda/turnos/" + creado.getId()))
                .body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtener(id));
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> actualizar(@PathVariable Long id, @RequestBody TurnoDTO dto) {
        return ResponseEntity.ok(turnoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{turnoId}/reservar/{pacienteId}")
    public ResponseEntity<TurnoDTO> reservar(@PathVariable Long turnoId, @PathVariable Long pacienteId) {
        return ResponseEntity.ok(turnoService.reservar(turnoId, pacienteId));
    }

    @PostMapping("/{turnoId}/cancelar")
    public ResponseEntity<TurnoDTO> cancelar(@PathVariable Long turnoId) {
        return ResponseEntity.ok(turnoService.cancelar(turnoId));
    }
}
