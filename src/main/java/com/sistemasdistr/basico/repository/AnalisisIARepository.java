package com.sistemasdistr.basico.repository;

import com.sistemasdistr.basico.model.AnalisisIA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalisisIARepository extends JpaRepository<AnalisisIA, Long> {

    List<AnalisisIA> findByUsuarioSolicitante_Username(String username);
}