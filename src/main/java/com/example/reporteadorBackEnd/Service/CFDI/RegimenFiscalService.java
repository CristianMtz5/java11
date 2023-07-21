package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.RegimenFiscalRepository;

@Service
public class RegimenFiscalService {
    @Autowired
    RegimenFiscalRepository regimenFiscalRepository; 

    public List<RegimenFiscalEntity> getAllRegimenFiscalByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<RegimenFiscalEntity> objetoImp = regimenFiscalRepository.findByStatus(status, sort);
        return objetoImp;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<RegimenFiscalEntity> regimenFiscal = regimenFiscalRepository.findByStatus(status, sort);
        return regimenFiscal.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(RegimenFiscalEntity regimenFiscal) {
        return new GlobalDTO(
            regimenFiscal
        );
    }
}
