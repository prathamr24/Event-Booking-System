package com.example.EventManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Theater name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    private Double latitude;

    private Double longitude;

    @NotBlank(message = "Contact number is required")
    @Column(nullable = false)
    private String contactNumber;
}