package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.PaisRepository;

@Service
public class EstadoService {
    
    @Autowired
    EstadoRepository estadoRepository; 

    @Autowired
    PaisRepository paisRepository;

    public List<EstadoEntity> getAllEstadoByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<EstadoEntity> estado = estadoRepository.findByStatus(status, sort);
        return estado;
    }

    public List<EstadoEntity> getByIdPaisAndStatus(String id, Boolean status, Sort sort){
        Optional<PaisEntity> paisEntity = paisRepository.findById(id);
        PaisEntity idPais = paisEntity.get();
        sort = Sort.by("id");

        List<EstadoEntity> estado = estadoRepository.findByIdPaisAndStatus(idPais, status, sort);
        return estado;
    }

    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<EstadoEntity> estado = estadoRepository.findByStatus(status, sort);
        return estado.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO entityToDTO(EstadoEntity estado) {
        return new GlobalDTO(
                estado
            );
    }
}
