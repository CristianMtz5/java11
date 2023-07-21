package com.example.reporteadorBackEnd.Entity.CFDI;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="Asentamientos")
public class AsentamientosEntity {
    @Id 
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column 
    private String cod;
    @Column 
    private String nombre;
    @Column 
    private String tipo;
    @Column 
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name="idCodigoPostal")
    private CodigoPostalEntity idCodigoPostal;

    @Override
    public String toString() {
        String colonias = id + " , " + nombre;
        return colonias;
    }
}
