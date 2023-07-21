package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.ExportacionRepository;

@Service
public class ExportacionService {
    @Autowired
    ExportacionRepository exportacionRepository; 

    public List<ExportacionEntity> getAllExportacionByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ExportacionEntity> exportacion = exportacionRepository.findByStatus(status, sort);
        return exportacion;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<ExportacionEntity> exportacion = exportacionRepository.findByStatus(status, sort);
        return exportacion.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(ExportacionEntity exportacion) {
        return new GlobalDTO(
                exportacion
            );
    }
}
