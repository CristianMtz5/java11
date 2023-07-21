package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoFactorRepository;

@Service
public class TipoFactorService {
    
    @Autowired
    TipoFactorRepository tipoFactorRepository; 

    public List<TipoFactorEntity> getAllTipoFactorByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TipoFactorEntity> TipoFactor = tipoFactorRepository.findByStatus(status, sort);
        return TipoFactor;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<TipoFactorEntity> tipoFactor = tipoFactorRepository.findByStatus(status, sort);
        return tipoFactor.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(TipoFactorEntity tipoFactor) {
        return new GlobalDTO(tipoFactor);
    }
}
