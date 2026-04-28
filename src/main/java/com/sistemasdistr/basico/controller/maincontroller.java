package com.sistemasdistr.basico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class maincontroller {

    // Pantalla 1: Dashboard (Inicio)
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    // Pantalla 2: Gestión de Usuarios
    @GetMapping("/usuarios")
    public String usuarios() {
        return "usuarios";
    }

    // Pantalla 3: Auditoría de Errores
    @GetMapping("/errores")
    public String errores() {
        return "errores";
    }

    // Pantalla 4: Laboratorio de IA
    @GetMapping("/laboratorio")
    public String laboratorio() {
        return "ia-lab";
    }

    // Pantalla de Login (No lleva Navbar)
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
