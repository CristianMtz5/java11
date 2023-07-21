package com.example.reporteadorBackEnd.Entity.CFDI.DTO;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsentamientosDTO {
    private String id;
    private String nombre;

    public AsentamientosDTO(AsentamientosEntity asentamientos){
        this.id = asentamientos.getId();
        this.nombre = asentamientos.getNombre();
    }
}
