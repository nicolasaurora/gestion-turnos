package com.agendadakota.agendadakota.controller;


import com.agendadakota.agendadakota.dto.ServicioDTO;
import com.agendadakota.agendadakota.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService ServicioService;

    public ServicioController(ServicioService servicioService) {
        this.ServicioService = servicioService;
    }

    //Crear
    @PostMapping
    public ResponseEntity<ServicioDTO> crear(@RequestBody ServicioDTO dto) {
        ServicioDTO creado = ServicioService.crear(dto);
        return ResponseEntity
                .created(URI.create("/agenda" + creado.getId()))
                .body(creado);
    }

    //Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ServicioService.obtener(id));
    }

    //Obtener todos
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> listar() {
        return ResponseEntity.ok(ServicioService.listar());
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(ServicioService.actualizar(id, dto));
    }

    //Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<ServicioDTO> eliminar(@PathVariable Long id) {
        ServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
