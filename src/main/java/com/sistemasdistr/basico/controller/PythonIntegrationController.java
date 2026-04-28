package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.service.PythonApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PythonIntegrationController {

    private final PythonApiService pythonApiService;

    public PythonIntegrationController(PythonApiService pythonApiService) {
        this.pythonApiService = pythonApiService;
    }

    @GetMapping("api-test")
    public String showApiTestPage() {
        return "api-test";
    }

    @GetMapping("/api/test/pokemon")
    public String testPokemon(Model model) {
        String resultado = pythonApiService.obtenerPokemon("digimon-no-existe");
        model.addAttribute("resultado", resultado);
        return "api-test";
    }

    @GetMapping("/api/test/archivo")
    public String testArchivo(Model model) {
        String resultado = pythonApiService.forzarErrorArchivo();
        model.addAttribute("resultado", resultado);
        return "api-test";
    }

    @GetMapping("/api/test/bd")
    public String testBd(Model model) {
        String resultado = pythonApiService.forzarErrorBaseDatos();
        model.addAttribute("resultado", resultado);
        return "api-test";
    }

}
