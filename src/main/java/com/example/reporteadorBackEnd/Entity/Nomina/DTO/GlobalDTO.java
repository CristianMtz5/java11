package com.example.reporteadorBackEnd.Entity.Nomina.DTO;

import com.example.reporteadorBackEnd.Entity.CFDI.AduanaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ClaveProdServEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.LocalidadEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MesesEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MetodoPagoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MunicipioEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.NumPedAduanaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ObjetoImpEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.PatenteAduanalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.PeriodicidadEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoRelacionEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.UsoCFDIEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalDTO {
    private String id;
    private String descripcion;

    public GlobalDTO(EmpresasEntity empresasEntity){
        this.id = empresasEntity.getId().toString();
        this.descripcion = empresasEntity.getNombre();
    }

    public GlobalDTO(AduanaEntity aduanaEntity){
        this.id = aduanaEntity.getId().toString();
        this.descripcion = aduanaEntity.getDescripcion();
    }

    public GlobalDTO(AsentamientosEntity asentamientosEntity){
        this.id = asentamientosEntity.getId();
        this.descripcion = asentamientosEntity.getNombre();
    }

    public GlobalDTO(ClaveProdServEntity claveProdServEntity){
        this.id = claveProdServEntity.getId();
        this.descripcion = claveProdServEntity.getDescripcion();
    }

    public GlobalDTO(ClaveUnidadEntity claveUnidadEntity){
        this.id = claveUnidadEntity.getId();
        this.descripcion = claveUnidadEntity.getDescripcion();
    }

    public GlobalDTO(CodigoPostalEntity codigoPostalEntity){
        this.id = codigoPostalEntity.getId();
        this.descripcion = codigoPostalEntity.getIdEstado().getNombreEstado();
        // String algo = codigoPostalEntity.getIdLocalidad().getId();
    }

    public GlobalDTO(EstadoEntity estadoEntity){
        this.id = estadoEntity.getId();
        this.descripcion = estadoEntity.getNombreEstado();
    }

    public GlobalDTO(ExportacionEntity exportacionEntity){
        this.id = exportacionEntity.getId();
        this.descripcion = exportacionEntity.getDescripcion();
    }

    public GlobalDTO(FormaPagoEntity formaPagoEntity){
        this.id = formaPagoEntity.getId();
        this.descripcion = formaPagoEntity.getDescripcion();
    }

    public GlobalDTO(ImpuestoEntity impuestoEntity){
        this.id = impuestoEntity.getId();
        this.descripcion = impuestoEntity.getDescripcion();
    }

    public GlobalDTO(LocalidadEntity localidadEntity){
        this.id = localidadEntity.getId();
        this.descripcion = localidadEntity.getDescripcion();
    }

    public GlobalDTO(MesesEntity mesesEntity){
        this.id = mesesEntity.getId();
        this.descripcion = mesesEntity.getDescripcion();
    }

    public GlobalDTO(MetodoPagoEntity metodoPagoEntity){
        this.id = metodoPagoEntity.getId();
        this.descripcion = metodoPagoEntity.getDescripcion();
    }

    public GlobalDTO(MonedaEntity monedaEntity){
        this.id = monedaEntity.getId();
        this.descripcion = monedaEntity.getDescripcion();
    }

    public GlobalDTO(MunicipioEntity municipioEntity){
        this.id = municipioEntity.getId();
        this.descripcion = municipioEntity.getDescripcion();
    }

    public GlobalDTO(NumPedAduanaEntity numPedAduanaEntity){
        this.id = numPedAduanaEntity.getId().toString();
        this.descripcion = numPedAduanaEntity.getPatente();
    }

    public GlobalDTO(ObjetoImpEntity objetoImpEntity){
        this.id = objetoImpEntity.getId();
        this.descripcion = objetoImpEntity.getDescripcion();
    }

    public GlobalDTO(PaisEntity paisEntity){
        this.id = paisEntity.getId();
        this.descripcion = paisEntity.getDescripcion();
    }

    public GlobalDTO(PatenteAduanalEntity patenteAduanalEntity){
        this.id = patenteAduanalEntity.getId();
        this.descripcion = " ";
        /* Agregar Descripcion */
    }

    public GlobalDTO(PeriodicidadEntity periodicidadEntity){
        this.id = periodicidadEntity.getId();
        this.descripcion = periodicidadEntity.getDescripcion();
    }

    public GlobalDTO(RegimenFiscalEntity regimenFiscalEntity){
        this.id = regimenFiscalEntity.getId();
        this.descripcion = regimenFiscalEntity.getDescripcion();
    }

    public GlobalDTO(TasaCuotaEntity tasaCuotaEntity){
        this.id = tasaCuotaEntity.getId().toString();
        this.descripcion = tasaCuotaEntity.getValorMaximo();
    }
    
    public GlobalDTO(TipoComprobanteEntity tipoComprobanteEntity){
        this.id = tipoComprobanteEntity.getId();
        this.descripcion = tipoComprobanteEntity.getDescripcion();
    }

    public GlobalDTO(TipoFactorEntity tipoFactorEntity){
        this.id = tipoFactorEntity.getId();
        this.descripcion = " ";
        /* Agregar Descripcion */
    }

    public GlobalDTO(TipoRelacionEntity tipoRelacionEntity){
        this.id = tipoRelacionEntity.getId();
        this.descripcion = tipoRelacionEntity.getDescripcion();
    }

    public GlobalDTO(UsoCFDIEntity usoCFDIEntity){
        this.id = usoCFDIEntity.getId();
        this.descripcion = usoCFDIEntity.getDescripcion();
    }
}
