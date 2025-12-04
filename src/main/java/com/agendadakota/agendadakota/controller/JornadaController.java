package com.agendadakota.agendadakota.controller;

import com.agendadakota.agendadakota.entities.Jornada;
import com.agendadakota.agendadakota.service.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jornadas")
@RequiredArgsConstructor
public class JornadaController {

    private final JornadaService jornadaService;

    @PostMapping
    public ResponseEntity<Jornada> crear(@RequestBody Jornada jornada) {
        return ResponseEntity.ok(jornadaService.crear(jornada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jornada> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jornadaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Jornada>> listar() {
        return ResponseEntity.ok(jornadaService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jornada> actualizar(@PathVariable Long id, @RequestBody Jornada jornada) {
        return ResponseEntity.ok(jornadaService.actualizar(id, jornada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        jornadaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
