package com.example.reporteadorBackEnd.Repository.Nomina;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import java.util.List;
import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {
    List<ClientesEntity> findByStatus(Boolean status, Sort sort);
    List<ClientesEntity> findByIdUsuarioAndStatus(UsuariosEntity idUsuario, Boolean status, Sort sort);
}
