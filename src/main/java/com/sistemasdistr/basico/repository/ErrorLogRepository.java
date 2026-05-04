package com.sistemasdistr.basico.repository;

import com.sistemasdistr.basico.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
    // Spring Data JPA ya nos da findById, findAll, save y deleteById gratis.
}