package com.interfija.masterposmultitenant.controller;

import com.interfija.masterposmultitenant.dtos.JwtAuthenticationResponse;
import com.interfija.masterposmultitenant.dtos.LoginRequest;
import com.interfija.masterposmultitenant.dtos.RegisterRequest;
import com.interfija.masterposmultitenant.dtos.ApiResponse;
import com.interfija.masterposmultitenant.model.User;
import com.interfija.masterposmultitenant.repository.UserRepository;
import com.interfija.masterposmultitenant.security.JwtTokenProvider;
import com.interfija.masterposmultitenant.services.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final TenantService tenantService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        // Verificar si el username ya existe
        if (userRepository.existsByUsername(registerRequest.username())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El nombre de usuario ya está en uso"));
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(registerRequest.email())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El email ya está registrado"));
        }

        // Verificar que el tenant existe
        if (!tenantService.existsByTenantId(registerRequest.tenantId())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El tenant especificado no existe"));
        }

        // Crear nuevo usuario
        User user = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .tenantId(registerRequest.tenantId())
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "Usuario registrado exitosamente"));
    }

    @PostMapping("/register-with-login")
    public ResponseEntity<?> registerAndLogin(@Valid @RequestBody RegisterRequest registerRequest) {

        // Verificar si el username ya existe
        if (userRepository.existsByUsername(registerRequest.username())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El nombre de usuario ya está en uso"));
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(registerRequest.email())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El email ya está registrado"));
        }

        // Verificar que el tenant existe
        if (!tenantService.existsByTenantId(registerRequest.tenantId())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El tenant especificado no existe"));
        }

        // Crear nuevo usuario
        User user = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .tenantId(registerRequest.tenantId())
                .enabled(true)
                .build();

        userRepository.save(user);

        // Autenticar automáticamente al usuario recién registrado
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.username(),
                        registerRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
