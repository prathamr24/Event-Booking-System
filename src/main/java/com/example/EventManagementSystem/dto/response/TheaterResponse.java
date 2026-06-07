package com.example.EventManagementSystem.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheaterResponse {

    private Long id;

    private String name;

    private String city;

    private String address;

    private Double latitude;

    private Double longitude;

    private String contactNumber;
}