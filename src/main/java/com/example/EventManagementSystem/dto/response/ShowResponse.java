package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.ShowStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ShowResponse {

    private Long id;

    private Long movieId;

    private String movieTitle;

    private Long screenId;

    private String screenName;

    private Long theaterId;

    private String theaterName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal basePrice;

    private ShowStatus status;
}