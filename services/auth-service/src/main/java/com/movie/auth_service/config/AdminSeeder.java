package com.movie.auth_service.config;

import com.movie.auth_service.entity.Role;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.entity.UserRole;
import com.movie.auth_service.repository.RoleRepository;
import com.movie.auth_service.repository.UserRepository;
import com.movie.auth_service.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String adminEmail = "admin@movie.com";

        // Create ROLE_ADMIN if not present
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {

                    Role role = Role.builder()
                            .name("ROLE_ADMIN")
                            .description("System Administrator")
                            .build();

                    return roleRepository.save(role);
                });

        // Admin already exists
        if (userRepository.existsByEmail(adminEmail)) {

            log.info("Default admin already exists.");

            return;
        }

        // Create Admin User
        User admin = User.builder()
                .firstName("System")
                .lastName("Administrator")
                .email(adminEmail)
                .password(passwordEncoder.encode("Admin@123"))
                .enabled(true)
                .emailVerified(true)
                .build();

        admin = userRepository.save(admin);

        // Assign ROLE_ADMIN
        UserRole userRole = UserRole.builder()
                .user(admin)
                .role(adminRole)
                .build();

        userRoleRepository.save(userRole);

        log.info("===========================================");
        log.info("Default Admin Created Successfully");
        log.info("Email    : {}", adminEmail);
        log.info("Password : Admin@123");
        log.info("Role     : ROLE_ADMIN");
        log.info("===========================================");
    }
}