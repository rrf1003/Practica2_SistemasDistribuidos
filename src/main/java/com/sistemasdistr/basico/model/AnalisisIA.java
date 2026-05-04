package com.sistemasdistr.basico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "analisis_ia")
@Getter
@Setter
public class AnalisisIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A qué usuario pertenece esta consulta
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usuarioSolicitante;

    @Column(length = 1000)
    private String textoOriginal;

    @Column(length = 2000)
    private String respuestaIA;

    private LocalDateTime fechaConsulta;

    public AnalisisIA() {
        this.fechaConsulta = LocalDateTime.now();
    }
}