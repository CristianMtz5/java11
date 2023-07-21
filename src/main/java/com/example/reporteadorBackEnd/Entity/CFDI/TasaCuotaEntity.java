package com.example.reporteadorBackEnd.Entity.CFDI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TasaCuota")
public class TasaCuotaEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NonNull
    private String rangoFijo;
    @Column
    @NonNull
    private String valorMinimo;
    @Column
    @NonNull
    private String valorMaximo;
    @Column
    @NonNull
    private String impuesto;
    @Column
    @NonNull
    private String factor;
    @Column
    @NonNull
    private String traslado;
    @Column
    @NonNull
    private String retencion;
    @Column
    @NonNull
    private Boolean status;
}

