package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingResponse {

    private Long bookingId;

    private String movieTitle;

    private String theaterName;

    private String screenName;

    private LocalDateTime showTime;

    private List<String> seats;

    private BigDecimal totalAmount;

    private BookingStatus status;
}
