package com.sistemasdistr.basico.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_busquedas")
public class RegistroBusqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String usuario;
    private String pokemon;

    // Le decimos que no actualice esta columna porque MySQL la rellena sola
    @Column(insertable = false, updatable = false)
    private LocalDateTime fecha;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPokemon() { return pokemon; }
    public void setPokemon(String pokemon) { this.pokemon = pokemon; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}