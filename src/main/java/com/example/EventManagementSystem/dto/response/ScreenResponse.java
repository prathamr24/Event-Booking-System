package com.example.EventManagementSystem.dto.response;

import com.example.EventManagementSystem.entity.ScreenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScreenResponse {

    private Long id;

    private Long theaterId;

    private String theaterName;

    private String name;

    private ScreenType screenType;

    private Integer totalSeats;
}
