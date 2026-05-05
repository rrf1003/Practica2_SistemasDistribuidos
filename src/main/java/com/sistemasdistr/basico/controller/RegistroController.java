package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.model.Role;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.RoleRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Mostrar la pantalla de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    // 2. Procesar los datos cuando el usuario pulsa "Registrarse"
    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model) {

        // VALIDACIÓN 1: ¿El usuario ya existe?
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Ese nombre de usuario ya está en uso. Elige otro.");
            return "registro";
        }

        // VALIDACIÓN 2: Complejidad de la contraseña (Regex)
        // (?=.*[a-z]) -> Al menos una minúscula
        // (?=.*[A-Z]) -> Al menos una mayúscula
        // (?=.*\d)    -> Al menos un número
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        if (!password.matches(regexPassword)) {
            model.addAttribute("error", "La contraseña debe contener al menos una mayúscula, una minúscula y un número.");
            return "registro";
        }

        // Si pasa las validaciones, creamos el usuario
        User nuevoUsuario = new User();
        nuevoUsuario.setUsername(username);
        // ¡Muy importante encriptar la contraseña antes de guardarla!
        nuevoUsuario.setPassword(passwordEncoder.encode(password));

        // Le asignamos el rol básico de usuario
        Role rolUsuario = roleRepository.findByName("ROLE_USER");
        if (rolUsuario == null) {
            rolUsuario = new Role();
            rolUsuario.setRoleName("ROLE_USER");
            roleRepository.save(rolUsuario);
        }
        nuevoUsuario.setUserRole(rolUsuario);

        // Guardamos en la base de datos
        userRepository.save(nuevoUsuario);

        // Redirigimos al login con un mensaje de éxito
        return "redirect:/login?exito=true";
    }
}