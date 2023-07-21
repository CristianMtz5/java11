package com.example.reporteadorBackEnd.Entity.Xml;

import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TrasladoOrRetencionXml")
public class TrasladoOrRetencionXmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double base;
    
    @Column(nullable = false)
    private Double importe;

    @Column
    private Boolean isTraslado;

    @Column
    private Boolean isRetencion;
    
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idImpuesto")
    private ImpuestoEntity idImpuesto;

    @ManyToOne
    @JoinColumn(name = "idTipoFactor")
    private TipoFactorEntity idTipoFactor;

    @ManyToOne
    @JoinColumn(name = "idTasaCuota")
    private TasaCuotaEntity idTasaCuota;

    @ManyToOne
    @JoinColumn(name = "idConceptoXml")
    private ConceptosXmlEntity idConcepto;
    
    @ManyToOne
    @JoinColumn(name = "idComprobanteXml")
    private ComprobanteXmlEntity idComprobante;
}
