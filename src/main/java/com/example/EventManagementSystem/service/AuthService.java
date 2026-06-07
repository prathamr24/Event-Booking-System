package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.LoginRequest;
import com.example.EventManagementSystem.dto.request.RegisterRequest;
import com.example.EventManagementSystem.dto.response.AuthResponse;
import com.example.EventManagementSystem.entity.Role;
import com.example.EventManagementSystem.entity.User;
import com.example.EventManagementSystem.exception.EmailAlreadyExistsException;
import com.example.EventManagementSystem.repository.UserRepository;
import com.example.EventManagementSystem.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);


        return new AuthResponse(token);
    }

    public AuthResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow();

        String token =
                jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}