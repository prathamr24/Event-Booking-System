package com.example.EventManagementSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TheaterRequest {

    @NotBlank(message = "Theater name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    private Double latitude;

    private Double longitude;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;
}