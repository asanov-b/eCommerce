package com.ecommerce.ecommerce.modules.auth.controller;

import com.ecommerce.ecommerce.modules.auth.dto.LoginDTO;
import com.ecommerce.ecommerce.modules.auth.dto.RefreshRequestDTO;
import com.ecommerce.ecommerce.modules.auth.dto.RegisterDTO;
import com.ecommerce.ecommerce.modules.auth.dto.TokenDTO;
import com.ecommerce.ecommerce.modules.auth.service.AuthService;
import com.ecommerce.ecommerce.modules.auth.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ecommerce.ecommerce.common.utils.AppPaths.*;

@RestController
@RequestMapping(API + VERSION + AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public HttpEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        TokenDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public HttpEntity<TokenDTO> refresh(@Valid @RequestBody RefreshRequestDTO tokenDTO) {
        TokenDTO tokens = refreshTokenService.rotateTokens(tokenDTO.token());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public HttpEntity<?> logout(@Valid @RequestBody RefreshRequestDTO tokenDTO) {
        refreshTokenService.revoke(tokenDTO.token());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
