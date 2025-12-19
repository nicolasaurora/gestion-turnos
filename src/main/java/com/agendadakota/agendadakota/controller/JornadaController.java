package com.agendadakota.agendadakota.controller;

import com.agendadakota.agendadakota.entities.Jornada;
import com.agendadakota.agendadakota.service.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jornadas")
@RequiredArgsConstructor
public class JornadaController {

    private final JornadaService jornadaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Jornada> crear(@RequestBody Jornada jornada) {
        return ResponseEntity.ok(jornadaService.crear(jornada));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Jornada> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jornadaService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Jornada>> listar() {
        return ResponseEntity.ok(jornadaService.listar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Jornada> actualizar(@PathVariable Long id, @RequestBody Jornada jornada) {
        return ResponseEntity.ok(jornadaService.actualizar(id, jornada));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        jornadaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
