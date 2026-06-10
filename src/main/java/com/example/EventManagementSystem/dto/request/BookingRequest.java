package com.example.EventManagementSystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    @NotNull(message = "Show id is required")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> showSeatIds;
}