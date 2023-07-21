package com.example.reporteadorBackEnd.Entity.Nomina;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.UsoCFDIEntity;
import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

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
@Table(name = "Clientes")
public class ClientesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false, length = 13)
    private String rfc;
    @Column
    private String codigoPostal;
    @Column
    private String correo;
    @Column
    private String telefono;
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idRegimenFiscal")
    private RegimenFiscalEntity idRegimenFiscal;

    @ManyToOne
    @JoinColumn(name = "idEmpresas")
    private EmpresasEntity idEmpresas;

    @ManyToOne
    @JoinColumn(name = "idUsoCfdi")
    private UsoCFDIEntity idUsoCfdi;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuariosEntity idUsuario;
}
