package com.ecommerce.ecommerce.modules.auth.service;

import com.ecommerce.ecommerce.common.exception.CustomException;
import com.ecommerce.ecommerce.modules.auth.dto.LoginDTO;
import com.ecommerce.ecommerce.modules.auth.dto.RegisterDTO;
import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;
import com.ecommerce.ecommerce.modules.user.entity.Role;
import com.ecommerce.ecommerce.modules.user.entity.enums.RoleName;
import com.ecommerce.ecommerce.modules.user.entity.User;
import com.ecommerce.ecommerce.modules.user.repository.RoleRepository;
import com.ecommerce.ecommerce.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            log.info("User {} logged in successfully", loginDTO.email());
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for username={}", loginDTO.email());
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        User user = userRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        return refreshTokenService.issueTokens(user);
    }

    @Transactional
    @Override
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.email())) {
            throw new CustomException(HttpStatus.CONFLICT, "Email already exists. email=" + registerDTO.email());
        }

        User user = new User();
        user.setEmail(registerDTO.email());
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        user.setFirstName(registerDTO.firstName());
        user.setLastName(registerDTO.lastName());
        List<Role> roleUser = roleRepository.findAllByRoleIn(List.of(RoleName.USER));
        user.setRoles(roleUser);
        userRepository.save(user);
        log.info("User registered successfully email={}", registerDTO.email());
    }
}