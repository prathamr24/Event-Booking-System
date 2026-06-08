package com.example.EventManagementSystem.dto.request;

import com.example.EventManagementSystem.entity.SeatType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatConfigurationRequest {

    @NotNull(message = "Start row is required")
    private Character startRow;

    @NotNull(message = "End row is required")
    private Character endRow;

    @NotNull(message = "Seats per row is required")
    private Integer seatsPerRow;

    @NotNull(message = "Seat type is required")
    private SeatType seatType;
}
