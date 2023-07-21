package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;

public interface PaisRepository extends JpaRepository<PaisEntity, String> {
    List<PaisEntity> findByStatus(Boolean status, Sort sort);
}
