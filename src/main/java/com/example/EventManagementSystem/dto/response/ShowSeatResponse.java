package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.SeatType;
import com.example.EventManagementSystem.entity.ShowSeatStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShowSeatResponse {

    private Long showSeatId;

    private Long seatId;

    private String seatNumber;

    private String rowLabel;

    private Integer seatIndex;

    private SeatType seatType;

    private BigDecimal price;

    private ShowSeatStatus status;
}
