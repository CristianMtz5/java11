package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.ImpuestoRepository;

@Service
public class ImpuestoService{
    @Autowired
    ImpuestoRepository impuestoRepository; 

    public List<ImpuestoEntity> getAllImpuestoByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ImpuestoEntity> impuesto = impuestoRepository.findByStatus(status, sort);
        return impuesto;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<ImpuestoEntity> impuesto = impuestoRepository.findByStatus(status, sort);
        return impuesto.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(ImpuestoEntity impuesto) {
        return new GlobalDTO(
                impuesto
            );
    }
}
