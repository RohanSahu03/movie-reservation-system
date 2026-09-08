package com.movie.auth_service.config;

import com.movie.auth_service.entity.Role;
import com.movie.auth_service.enums.RoleType;
import com.movie.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        createRoleIfNotExists(
                RoleType.CUSTOMER.name(),
                "Customer who can book movie tickets"
        );

        createRoleIfNotExists(
                RoleType.ADMIN.name(),
                "System Administrator"
        );

        createRoleIfNotExists(
                RoleType.THEATRE_OWNER.name(),
                "Theatre Owner"
        );

        log.info("Role initialization completed.");
    }

    private void createRoleIfNotExists(String roleName, String description) {

        if (!roleRepository.existsByName(roleName)) {

            Role role = Role.builder()
                    .name(roleName)
                    .description(description)
                    .build();

            roleRepository.save(role);

            log.info("Created role: {}", roleName);

        } else {

            log.info("Role already exists: {}", roleName);

        }
    }
}