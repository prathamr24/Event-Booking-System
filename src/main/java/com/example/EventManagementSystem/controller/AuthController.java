package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.request.LoginRequest;
import com.example.EventManagementSystem.dto.request.RegisterRequest;
import com.example.EventManagementSystem.dto.response.AuthResponse;
import com.example.EventManagementSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}
