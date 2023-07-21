package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.DTO.AsentamientosDTO;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.AsentamientosRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.CodigoPostalRepository;

@Service
public class AsentamientosService {
    @Autowired
    AsentamientosRepository asentamientosRepository;
    
    @Autowired
    CodigoPostalRepository codigoPostalRepository;

    public List<AsentamientosEntity> getByIdCodigoPostalAndStatus(String id, Boolean status, Sort sort){
        Optional<CodigoPostalEntity> codigoPostalEntity = codigoPostalRepository.findById(id);
        CodigoPostalEntity idCodPostal = codigoPostalEntity.get();
        
        sort = Sort.by("id");
        List<AsentamientosEntity> asentamientos = asentamientosRepository.findByIdCodigoPostalAndStatus(idCodPostal, status, sort);
        return asentamientos;
    }

    public List<AsentamientosDTO> getByIdCodigoPostal(String id) {
        Optional<CodigoPostalEntity> codigoPostalEntity = codigoPostalRepository.findById(id);
        CodigoPostalEntity idCodPostal = codigoPostalEntity.get();

        List<AsentamientosEntity> asentamientos = asentamientosRepository.findByIdCodigoPostal(idCodPostal);
        return asentamientos.stream()
                .map(this::asentamientosEntityToDTO)
                .collect(Collectors.toList());
    }

    public AsentamientosDTO asentamientosEntityToDTO(AsentamientosEntity asentamientos) {
        return new AsentamientosDTO(
                asentamientos
            );
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<AsentamientosEntity> asentamientos = asentamientosRepository.findByStatus(status, sort);
        return asentamientos.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(AsentamientosEntity asentamientos) {
        return new GlobalDTO(
                asentamientos
            );
    }
}
