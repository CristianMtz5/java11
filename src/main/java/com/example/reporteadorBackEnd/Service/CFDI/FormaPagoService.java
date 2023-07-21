package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.FormaPagoRepository;

@Service
public class FormaPagoService {
    
    @Autowired
    FormaPagoRepository formaPagoRepository; 

    public List<FormaPagoEntity> getAllFormaPagoByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<FormaPagoEntity> formaPago = formaPagoRepository.findByStatus(status, sort);
        return formaPago;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<FormaPagoEntity> formaPago = formaPagoRepository.findByStatus(status, sort);
        return formaPago.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(FormaPagoEntity formaPago) {
        return new GlobalDTO(
                formaPago
            );
    }
}
