package com.ecommerce.ecommerce.common.component;

import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.RoleName;
import com.ecommerce.ecommerce.modules.user.entity.User;
import com.ecommerce.ecommerce.modules.user.repository.RoleRepository;
import com.ecommerce.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin.password}")
    private String adminPassword;
    @Value("${app.seed.user.password}")
    private String userPassword;

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = roleRepository.findByRole(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("ADMIN role not seeded"));
        Role userRole = roleRepository.findByRole(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException("USER role not seeded"));

        userRepository.findByEmail("admin@gmail.com").orElseGet(() ->
                userRepository.save(new User(
                        "admin@gmail.com",
                        passwordEncoder.encode(adminPassword),
                        "Admin",
                        "Admin",
                        List.of(adminRole)
                ))
        );

        userRepository.findByEmail("user@gmail.com").orElseGet(() ->
                userRepository.save(new User(
                        "user@gmail.com",
                        passwordEncoder.encode(userPassword),
                        "User",
                        "User",
                        List.of(userRole)
                ))
        );
    }
}
