package com.sistemasdistr.basico.repository;

import com.sistemasdistr.basico.model.RegistroBusqueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroBusquedaRepository extends JpaRepository<RegistroBusqueda, Integer> {
    // Spring Data JPA hace toda la magia por nosotros, no hay que programar nada más aquí.
}