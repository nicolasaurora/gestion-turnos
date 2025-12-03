package com.agendadakota.agendadakota.service.impl;

import com.agendadakota.agendadakota.dto.ServicioDTO;
import com.agendadakota.agendadakota.entities.Servicio;
import com.agendadakota.agendadakota.repository.ServicioRepository;
import com.agendadakota.agendadakota.service.ServicioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {
    private final ServicioRepository servicioRepository;
    private final ModelMapper mapper;

    public ServicioServiceImpl(ServicioRepository servicioRepository, ModelMapper mapper) {
        this.servicioRepository = servicioRepository;
        this.mapper = mapper;
    }

    @Override
    public ServicioDTO crear(ServicioDTO dto) {
        var entidad = mapper.map(dto, Servicio.class);
        entidad = servicioRepository.save(entidad);
        return mapper.map(entidad, ServicioDTO.class);
    }

    @Override
    public ServicioDTO obtener(Long id) {
        var entidad = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return mapper.map(entidad, ServicioDTO.class);
    }

    @Override
    public List<ServicioDTO> listar() {
        return servicioRepository.findAll()
                .stream()
                .map(s -> mapper.map(s, ServicioDTO.class))
                .toList();
    }

    @Override
    public ServicioDTO actualizar(Long id, ServicioDTO dto) {
        var entidad = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setPrecio(dto.getPrecio());

        servicioRepository.save(entidad);

        return mapper.map(entidad, ServicioDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        var entidad = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));

        servicioRepository.deleteById(id);

    }
}
