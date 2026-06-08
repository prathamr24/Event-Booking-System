package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.ScreenRequest;
import com.example.EventManagementSystem.dto.request.SeatConfigurationRequest;
import com.example.EventManagementSystem.dto.response.ScreenResponse;
import com.example.EventManagementSystem.dto.response.SeatResponse;
import com.example.EventManagementSystem.entity.*;
import com.example.EventManagementSystem.exception.InvalidSeatConfigurationException;
import com.example.EventManagementSystem.exception.ResourceNotFoundException;
import com.example.EventManagementSystem.exception.ScreenAlreadyExistsException;
import com.example.EventManagementSystem.repository.ScreenRepository;
import com.example.EventManagementSystem.repository.SeatRepository;
import com.example.EventManagementSystem.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final TheaterRepository theaterRepository;

    @Transactional
    public ScreenResponse createScreen(
            ScreenRequest request
    ) {

        validateConfigurations(
                request.getSeatConfigurations()
        );

        Theater theater = theaterRepository
                .findById(request.getTheaterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id: "
                                        + request.getTheaterId()
                        ));

        if (
                screenRepository
                        .existsByTheaterIdAndName(
                                theater.getId(),
                                request.getName()
                        )
        ) {

            throw new ScreenAlreadyExistsException(
                    "Screen with name '"
                            + request.getName()
                            + "' already exists in theater '"
                            + theater.getName()
                            + "'"
            );
        }

        Screen screen = Screen.builder()
                .theater(theater)
                .name(request.getName())
                .screenType(request.getScreenType())
                .totalSeats(0)
                .build();

        Screen savedScreen =
                screenRepository.save(screen);

        List<Seat> seats =
                generateSeats(
                        savedScreen,
                        request.getSeatConfigurations()
                );

        seatRepository.saveAll(seats);

        savedScreen.setTotalSeats(seats.size());

        screenRepository.save(savedScreen);

        return mapToResponse(savedScreen);
    }

    private List<Seat> generateSeats(
            Screen screen,
            List<SeatConfigurationRequest> configurations
    ) {

        List<Seat> seats = new ArrayList<>();

        for (SeatConfigurationRequest config : configurations) {

            for (
                    char row = config.getStartRow();
                    row <= config.getEndRow();
                    row++
            ) {

                for (
                        int seatNumber = 1;
                        seatNumber <= config.getSeatsPerRow();
                        seatNumber++
                ) {

                    seats.add(
                            Seat.builder()
                                    .screen(screen)
                                    .seatNumber(
                                            row + String.valueOf(seatNumber)
                                    )
                                    .rowLabel(
                                            String.valueOf(row)
                                    )
                                    .seatIndex(seatNumber)
                                    .seatType(
                                            config.getSeatType()
                                    )
                                    .build()
                    );
                }
            }
        }

        return seats;
    }

    private ScreenResponse mapToResponse(
            Screen screen
    ) {

        return ScreenResponse.builder()
                .id(screen.getId())
                .theaterId(
                        screen.getTheater().getId()
                )
                .theaterName(
                        screen.getTheater().getName()
                )
                .name(screen.getName())
                .screenType(screen.getScreenType())
                .totalSeats(screen.getTotalSeats())
                .build();
    }

    private void validateConfigurations(
            List<SeatConfigurationRequest> configurations
    ) {



        Set<Character> occupiedRows =
                new HashSet<>();

        for (
                SeatConfigurationRequest config
                : configurations
        ) {

            if (
                    config.getStartRow()
                            > config.getEndRow()
            ) {

                throw new InvalidSeatConfigurationException(
                        "Start row cannot be greater than end row"
                );
            }

            for (
                    char row = config.getStartRow();
                    row <= config.getEndRow();
                    row++
            ) {

                if (
                        occupiedRows.contains(row)
                ) {

                    throw new InvalidSeatConfigurationException(
                            "Row "
                                    + row
                                    + " is configured multiple times"
                    );
                }

                occupiedRows.add(row);
            }
        }
    }

    public ScreenResponse getScreenById(
            Long screenId
    ) {

        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + screenId
                        ));

        return mapToResponse(screen);
    }

    public List<ScreenResponse> getScreensByTheater(
            Long theaterId
    ) {

        theaterRepository.findById(theaterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theater not found with id: "
                                        + theaterId
                        ));

        return screenRepository
                .findByTheaterId(theaterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<SeatResponse> getSeatsByScreen(
            Long screenId
    ) {

        screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: "
                                        + screenId
                        ));

        return seatRepository
                .findByScreenId(screenId)
                .stream()
                .map(this::mapSeatResponse)
                .toList();
    }

    private SeatResponse mapSeatResponse(
            Seat seat
    ) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowLabel(seat.getRowLabel())
                .seatIndex(seat.getSeatIndex())
                .seatType(seat.getSeatType())
                .build();
    }
}