package com.example.EventManagementSystem.controller;

import com.example.EventManagementSystem.dto.request.ShowRequest;
import com.example.EventManagementSystem.dto.response.ShowResponse;
import com.example.EventManagementSystem.dto.response.ShowSeatResponse;
import com.example.EventManagementSystem.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ShowResponse createShow(
            @Valid
            @RequestBody
            ShowRequest request
    ) {

        return showService.createShow(
                request
        );
    }

    @GetMapping("/{showId}/seats")
    @PreAuthorize(
            "hasAnyRole('ADMIN','CUSTOMER','ORGANIZER')"
    )
    public List<ShowSeatResponse> getShowSeats(
            @PathVariable Long showId
    ) {

        return showService.getShowSeats(
                showId
        );
    }
}