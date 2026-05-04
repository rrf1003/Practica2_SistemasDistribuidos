package com.sistemasdistr.basico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "error_logs")
@Getter
@Setter
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 1000) // Para que MySQL permita textos largos
    private String detalle;

    private String tipoExcepcion;

    private LocalDateTime fechaHora;

    // Puedes añadir una nota si un administrador lo revisa
    private String notasAdministrador;

    // Constructor vacío requerido por JPA
    public ErrorLog() {
        this.fechaHora = LocalDateTime.now();
    }
}