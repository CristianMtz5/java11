package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoComprobanteRepository;

@Service
public class TipoComprobanteService {
    @Autowired
    TipoComprobanteRepository tipoCompRepository; 

    public List<TipoComprobanteEntity> getAllTipoCompByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TipoComprobanteEntity> tipoComp = tipoCompRepository.findByStatus(status, sort);
        return tipoComp;
    }  

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<TipoComprobanteEntity> tipoComprobante = tipoCompRepository.findByStatus(status, sort);
        return tipoComprobante.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(TipoComprobanteEntity tipoComprobante) {
        return new GlobalDTO(tipoComprobante);
    }
}
