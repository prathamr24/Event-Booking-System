package com.example.EventManagementSystem.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShowRequest {

    @NotNull(message = "Movie id is required")
    private Long movieId;

    @NotNull(message = "Screen id is required")
    private Long screenId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "Base price is required")
    @DecimalMin(
            value = "1.0",
            message = "Base price must be greater than zero"
    )
    private BigDecimal basePrice;
}