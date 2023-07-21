package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.MonedaRepository;

@Service
public class MonedaService {
    
    @Autowired
    MonedaRepository monedaRepository; 

    public List<MonedaEntity> getAllMonedaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<MonedaEntity> Moneda = monedaRepository.findByStatus(status, sort);
        return Moneda;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<MonedaEntity> moneda = monedaRepository.findByStatus(status, sort);
        return moneda.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(MonedaEntity moneda) {
        return new GlobalDTO(
                moneda
            );
    }
}
