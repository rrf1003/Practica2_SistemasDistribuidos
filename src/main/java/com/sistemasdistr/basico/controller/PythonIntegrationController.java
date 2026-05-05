package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.service.PythonApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class PythonIntegrationController {

    private final PythonApiService pythonApiService;

    public PythonIntegrationController(PythonApiService pythonApiService) {
        this.pythonApiService = pythonApiService;
    }

    // Ruta para mostrar la pantalla
    @GetMapping("/api-test")
    public String showApiTestPage() {
        return "api-test";
    }

    // AHORA ES INTERACTIVO: Recibe la variable "nombre" desde el HTML
    @GetMapping("/api/test/pokemon")
    public String testPokemon(@RequestParam("nombre") String nombre, Principal principal, Model model) {
        try {
            // Sacamos el nombre del usuario logueado (si no hay, ponemos Anónimo)
            String nombreUsuario = (principal != null) ? principal.getName() : "Anónimo";

            // Le pasamos las dos cosas a nuestro servicio
            String resultado = pythonApiService.obtenerPokemon(nombre, nombreUsuario);

            model.addAttribute("resultado", resultado);
        } catch (Exception e) {
            model.addAttribute("resultado", "Error al conectar con Python: " + e.getMessage());
        }
        return "api-test";
    }

    @GetMapping("/api/test/archivo")
    public String testArchivo(Model model) {
        try {
            String resultado = pythonApiService.forzarErrorArchivo();
            model.addAttribute("resultado", resultado);
        } catch (Exception e) {
            model.addAttribute("resultado", "Error al conectar con Python: " + e.getMessage());
        }
        return "api-test";
    }

    @GetMapping("/api/test/bd")
    public String testBd(Model model) {
        try {
            String resultado = pythonApiService.forzarErrorBaseDatos();
            model.addAttribute("resultado", resultado);
        } catch (Exception e) {
            model.addAttribute("resultado", "Error al conectar con Python: " + e.getMessage());
        }
        return "api-test";
    }
}