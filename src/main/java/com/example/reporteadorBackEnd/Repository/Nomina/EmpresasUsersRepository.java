package com.example.reporteadorBackEnd.Repository.Nomina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasUsersEntity;
import com.example.reporteadorBackEnd.Repository.Nomina.Interface.EmpresasUserInterface;
public interface EmpresasUsersRepository extends JpaRepository<EmpresasUsersEntity, Long>{
    @Query(value="SELECT e.id, e.nombre FROM empresas e INNER JOIN empresas_users_control ec ON e.id = ec.id_empresas WHERE id_usuarios = ?1 GROUP BY e.id, e.nombre", nativeQuery = true)    
    List<EmpresasUserInterface> getEmpresasByIdUser(Integer id);
}
