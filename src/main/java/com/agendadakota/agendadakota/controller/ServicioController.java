package com.agendadakota.agendadakota.controller;


import com.agendadakota.agendadakota.dto.ServicioDTO;
import com.agendadakota.agendadakota.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agenda/servicios")
public class ServicioController {

    private final ServicioService ServicioService;

    public ServicioController(ServicioService servicioService) {
        this.ServicioService = servicioService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServicioDTO> crear(@RequestBody ServicioDTO dto) {
        ServicioDTO creado = ServicioService.crear(dto);
        return ResponseEntity
                .created(URI.create("/agenda/servicios" + creado.getId()))
                .body(creado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ServicioService.obtener(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> listar() {
        return ResponseEntity.ok(ServicioService.listar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(ServicioService.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ServicioDTO> eliminar(@PathVariable Long id) {
        ServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
