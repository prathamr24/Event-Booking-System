package com.example.EventManagementSystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public")
    public String publicApi() {
        return "Public API";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer")
    public String customerApi() {
        return "Customer API";
    }

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping("/organizer")
    public String organizerApi() {
        return "Organizer API";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminApi() {
        return "Admin API";
    }
}
