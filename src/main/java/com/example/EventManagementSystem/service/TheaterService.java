package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.TheaterRequest;
import com.example.EventManagementSystem.dto.response.TheaterResponse;
import com.example.EventManagementSystem.entity.Theater;
import com.example.EventManagementSystem.exception.ResourceNotFoundException;
import com.example.EventManagementSystem.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterResponse createTheater(
            TheaterRequest request
    ) {

        Theater theater = Theater.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .contactNumber(request.getContactNumber())
                .build();

        Theater savedTheater =
                theaterRepository.save(theater);

        return mapToResponse(savedTheater);
    }

    public List<TheaterResponse> getAllTheaters() {

        return theaterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TheaterResponse getTheaterById(
            Long theaterId
    ) {

        Theater theater =
                theaterRepository.findById(theaterId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Theater not found with id: "
                                                + theaterId
                                ));

        return mapToResponse(theater);
    }

    private TheaterResponse mapToResponse(
            Theater theater
    ) {

        return TheaterResponse.builder()
                .id(theater.getId())
                .name(theater.getName())
                .city(theater.getCity())
                .address(theater.getAddress())
                .latitude(theater.getLatitude())
                .longitude(theater.getLongitude())
                .contactNumber(theater.getContactNumber())
                .build();
    }
}