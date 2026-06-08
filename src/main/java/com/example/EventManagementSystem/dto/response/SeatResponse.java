package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.SeatType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private String rowLabel;

    private Integer seatIndex;

    private SeatType seatType;
}