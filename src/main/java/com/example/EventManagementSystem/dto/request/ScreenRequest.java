package com.example.EventManagementSystem.dto.request;

import com.example.EventManagementSystem.entity.ScreenType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ScreenRequest {

    @NotNull(message = "Theater id is required")
    private Long theaterId;

    @NotBlank(message = "Screen name is required")
    private String name;

    @NotNull(message = "Screen type is required")
    private ScreenType screenType;

    @Valid
    @NotEmpty(message = "Seat configurations are required")
    private List<SeatConfigurationRequest> seatConfigurations;
}