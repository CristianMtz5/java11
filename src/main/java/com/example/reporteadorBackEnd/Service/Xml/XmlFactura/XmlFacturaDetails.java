package com.example.reporteadorBackEnd.Service.Xml.XmlFactura;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class XmlFacturaDetails {
    private String claveProdServ;
    // private String noIdentificaion;
    private String cantidad;
    private String claveUnidad;
    private String unidad;
    private String valorUnitario;
    private String importe;
    private String descuento;
    private String descripcion;
    private String impuesto;
    private String base;
    private String tipoFactor;
    private String tasaCuota;
    private String importeTraslado;
}