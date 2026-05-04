package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.repository.ErrorLogRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminCRUDController {

    private final UserRepository userRepository;
    private final ErrorLogRepository errorLogRepository;

    public AdminCRUDController(UserRepository userRepository, ErrorLogRepository errorLogRepository) {
        this.userRepository = userRepository;
        this.errorLogRepository = errorLogRepository;
    }

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        // Buscamos todos los usuarios y los mandamos a la vista
        model.addAttribute("listaUsuarios", userRepository.findAll());
        return "usuarios"; // Nombre del archivo HTML
    }

    @PostMapping("/admin/usuarios/eliminar/{id}")
    public String borrarUsuario(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/usuarios"; // Recarga la página tras borrar
    }

    @GetMapping("/admin/errores")
    public String listarErrores(Model model) {
        // Buscamos todos los errores registrados
        model.addAttribute("listaErrores", errorLogRepository.findAll());
        return "errores";
    }

    @PostMapping("/admin/errores/eliminar/{id}")
    public String borrarError(@PathVariable Long id) {
        errorLogRepository.deleteById(id);
        return "redirect:/admin/errores";
    }
}