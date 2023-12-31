package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.LocalidadEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.LocalidadRepository;

@Service
public class LocalidadService {

    @Autowired
    LocalidadRepository localidadRepository; 

    @Autowired
    EstadoRepository estadoRepository;

    public List<LocalidadEntity> getAllLocalidadByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<LocalidadEntity> localidad = localidadRepository.findByStatus(status, sort);
        return localidad;
    }

    public List<LocalidadEntity> getByEstadoAndStatus(String idEstado, Boolean status){
        Optional<EstadoEntity> estado = estadoRepository.findById(idEstado);
        EstadoEntity estadoId = estado.get();
        return localidadRepository.findByIdEstadoAndStatus(estadoId, status);
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<LocalidadEntity> localidad = localidadRepository.findByStatus(status, sort);
        return localidad.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(LocalidadEntity localidad) {
        return new GlobalDTO(
                localidad
            );
    }
}
