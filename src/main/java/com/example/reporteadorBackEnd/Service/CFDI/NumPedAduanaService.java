package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.NumPedAduanaEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.NumPedAduanaRepository;

@Service
public class NumPedAduanaService {
    
    @Autowired
    NumPedAduanaRepository numPedAduanaRepository; 

    public List<NumPedAduanaEntity> getAllNumPedAduanaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<NumPedAduanaEntity> numPedAduana = numPedAduanaRepository.findByStatus(status, sort);
        return numPedAduana;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<NumPedAduanaEntity> numPedAduana = numPedAduanaRepository.findByStatus(status, sort);
        return numPedAduana.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(NumPedAduanaEntity numPedAduana) {
        return new GlobalDTO(
            numPedAduana
        );
    }
}
