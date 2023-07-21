package com.example.reporteadorBackEnd.Entity.CFDI.DTO;

import java.util.ArrayList;
import java.util.List;

import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodigoPostalDTO {
    private String idEstado;
    private String nombreEstado;
    private String codMunicipio;
    private String nombreMunicipio;
    private String codLocalidad;
    private String nombreLocalidad;
    private String idCodigoPostal;
    List<AsentamientosDTO> localidades = new ArrayList<>();

    public CodigoPostalDTO(CodigoPostalEntity codigoPostalEntity){
        this.idEstado = codigoPostalEntity.getIdEstado().getId();
        this.nombreEstado = codigoPostalEntity.getIdEstado().getNombreEstado();
        this.codMunicipio = codigoPostalEntity.getIdMunicipio().getCod();
        this.nombreMunicipio = codigoPostalEntity.getIdMunicipio().getDescripcion();
        this.codLocalidad = codigoPostalEntity.getIdLocalidad().getCod();
        this.nombreLocalidad = codigoPostalEntity.getIdLocalidad().getDescripcion();
        this.idCodigoPostal = codigoPostalEntity.getId();
    }
}
