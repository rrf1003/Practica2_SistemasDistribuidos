package com.sistemasdistr.basico.config;

import com.sistemasdistr.basico.model.Role;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.RoleRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole;
            if (roleRepository.count() == 0) {
                adminRole = new Role(null, "ROLE_ADMIN", 1);
                adminRole = roleRepository.save(adminRole);
            } else {
                adminRole = roleRepository.findAll().get(0);
            }

            if (userRepository.findUserByUsername("admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setEmail("admin@ejemplo.com");
                adminUser.setNombreUsuario("Administrador del Sistema");
                adminUser.setFechaUltimoAcceso(LocalDateTime.now());
                adminUser.setUserRole(adminRole);

                userRepository.save(adminUser);
            }
        };
    }
}
