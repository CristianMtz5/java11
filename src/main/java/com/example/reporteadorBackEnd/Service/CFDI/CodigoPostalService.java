package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.DTO.AsentamientosDTO;
import com.example.reporteadorBackEnd.Entity.CFDI.DTO.CodigoPostalDTO;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.GlobalDTO;
import com.example.reporteadorBackEnd.Repository.CFDI.AsentamientosRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.CodigoPostalRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;

@Service
public class CodigoPostalService {
    @Autowired
    CodigoPostalRepository codigoPostalRepository; 

    @Autowired
    AsentamientosRepository asentamientosRepository;

    @Autowired
    AsentamientosService asentamientosService;

    @Autowired
    EstadoRepository estadoRepository;

    public List<CodigoPostalDTO> getByIdConAsentamientos(String id) {
        Optional<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findById(id);

        List<AsentamientosDTO> asentamientosDTOs = asentamientosService.getByIdCodigoPostal(id);

        List<CodigoPostalDTO> codigoLista2 = codigoPostal.stream()
                        .map(this::codigoPostalEntityToDTO)
                        .collect(Collectors.toList());

        codigoLista2.get(0).setLocalidades(asentamientosDTOs);
        
        return codigoLista2;
    }

    public CodigoPostalDTO codigoPostalEntityToDTO(CodigoPostalEntity codigoPostal) {
        return new CodigoPostalDTO(
                codigoPostal
            );
    }

    /* ************************************************************************* */
    public List<GlobalDTO> getAll(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByStatus(status, sort);
        
        return codigoPostal.stream()
                .map(this::codigoPostalEntityToGlobalDTO)
                .collect(Collectors.toList());
    }

    public List<GlobalDTO> getById(String id) {
        Optional<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findById(id);
        
        return codigoPostal.stream()
                .map(this::codigoPostalEntityToGlobalDTO)
                .collect(Collectors.toList());
    }

    public GlobalDTO codigoPostalEntityToGlobalDTO(CodigoPostalEntity asentamientos) {
        return new GlobalDTO(
                asentamientos
            );
    }

    /* *************************************************************************************** */
    public List<CodigoPostalEntity> getAllCodigoPostalByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByStatus(status, sort);
        return codigoPostal;
    }

    public Optional<CodigoPostalEntity> getByIdAndStatus(String id, Boolean status, Sort sort){
        sort = Sort.by("id");
        Optional<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByIdAndStatus(id, status, sort);
        return codigoPostal;
    }

    public List<CodigoPostalEntity> getByIdEstadoAndStatus(String id, Boolean status, Sort sort){
        Optional<EstadoEntity> estadoEntity = estadoRepository.findById(id);
        EstadoEntity idEstado = estadoEntity.get();
        
        sort = Sort.by("id");
        List<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByIdEstadoAndStatus(idEstado, status, sort);
        return codigoPostal;
    }
}
