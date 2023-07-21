package com.example.reporteadorBackEnd.Entity.Nomina;

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

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Empresas")
public class EmpresasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String nombre;
    @Column(length = 8)
    private String cp;
    @Column(columnDefinition = "TEXT", length = 3000)
    private String cerB64;
    @Column(columnDefinition = "TEXT", length = 3000)
    private String routeCerB64;
    @Column(columnDefinition = "TEXT", length = 3000)
    private String keyB64;
    @Column
    private String passKey;
    @Column
    private String numCertificado;
    @Column
    private String usuarioPac;
    @Column
    private String contraseñaPac;
    @Column
    private String fisica;
    @Column
    private String rfc;
    @Column
    private String curp;
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idRegimenFiscal")
    private RegimenFiscalEntity idRegimenFiscal;
}
