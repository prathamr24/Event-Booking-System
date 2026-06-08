package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.request.ScreenRequest;
import com.example.EventManagementSystem.dto.response.ScreenResponse;
import com.example.EventManagementSystem.dto.response.SeatResponse;
import com.example.EventManagementSystem.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ScreenResponse createScreen(
            @Valid
            @RequestBody
            ScreenRequest request
    ) {

        return screenService.createScreen(
                request
        );
    }


    @GetMapping("/{id}")
    public ScreenResponse getScreenById(
            @PathVariable Long id
    ) {

        return screenService.getScreenById(id);
    }

    @GetMapping("/theater/{theaterId}")
    public List<ScreenResponse> getScreensByTheater(
            @PathVariable Long theaterId
    ) {

        return screenService.getScreensByTheater(
                theaterId
        );
    }

    @GetMapping("/{id}/seats")
    public List<SeatResponse> getSeatsByScreen(
            @PathVariable Long id
    ) {

        return screenService.getSeatsByScreen(id);
    }
}