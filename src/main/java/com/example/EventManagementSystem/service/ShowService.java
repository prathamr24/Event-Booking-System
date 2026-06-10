package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.ShowRequest;
import com.example.EventManagementSystem.dto.response.ShowResponse;
import com.example.EventManagementSystem.dto.response.ShowSeatResponse;
import com.example.EventManagementSystem.entity.*;
import com.example.EventManagementSystem.exception.ResourceNotFoundException;
import com.example.EventManagementSystem.exception.ShowConflictException;
import com.example.EventManagementSystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public ShowResponse createShow(
            ShowRequest request
    ) {

        Movie movie =
                movieRepository.findById(
                                request.getMovieId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Movie not found with id: "
                                                + request.getMovieId()
                                ));

        Screen screen =
                screenRepository.findById(
                                request.getScreenId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Screen not found with id: "
                                                + request.getScreenId()
                                ));

        LocalDateTime endTime =
                request.getStartTime()
                        .plusMinutes(
                                movie.getDurationMinutes()
                        );

        boolean conflictingShow =
                showRepository
                        .existsConflictingShow(
                                screen.getId(),
                                request.getStartTime(),
                                endTime
                        );

        if (conflictingShow) {

            throw new ShowConflictException(
                    "Another show already exists during this time slot"
            );
        }

        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .startTime(
                        request.getStartTime()
                )
                .endTime(endTime)
                .basePrice(
                        request.getBasePrice()
                )
                .status(
                        ShowStatus.SCHEDULED
                )
                .build();

        Show savedShow =
                showRepository.save(show);

        generateShowSeats(savedShow);

        return mapToResponse(savedShow);
    }

    public List<ShowSeatResponse> getShowSeats(
            Long showId
    ) {

        Show show = showRepository
                .findById(showId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: "
                                        + showId
                        ));

        return showSeatRepository
                .findByShowIdOrderBySeat_RowLabelAscSeat_SeatIndexAsc(
                        show.getId()
                )
                .stream()
                .map(this::mapShowSeatResponse)
                .toList();
    }


    private void generateShowSeats(
            Show show
    ) {

        List<Seat> seats =
                seatRepository.findByScreenId(
                        show.getScreen().getId()
                );

        List<ShowSeat> showSeats =
                seats.stream()
                        .map(seat ->
                                ShowSeat.builder()
                                        .show(show)
                                        .seat(seat)
                                        .status(
                                                ShowSeatStatus.AVAILABLE
                                        )
                                        .price(
                                                calculatePrice(
                                                        show.getBasePrice(),
                                                        seat.getSeatType()
                                                )
                                        )
                                        .build()
                        )
                        .toList();

        showSeatRepository.saveAll(showSeats);
    }

    private BigDecimal calculatePrice(
            BigDecimal basePrice,
            SeatType seatType
    ) {

        return switch (seatType) {

            case REGULAR ->
                    basePrice;

            case PREMIUM ->
                    basePrice.multiply(
                            BigDecimal.valueOf(1.5)
                    );

            case RECLINER ->
                    basePrice.multiply(
                            BigDecimal.valueOf(2.0)
                    );
        };
    }

    private ShowResponse mapToResponse(
            Show show
    ) {

        return ShowResponse.builder()
                .id(show.getId())
                .movieId(
                        show.getMovie().getId()
                )
                .movieTitle(
                        show.getMovie().getTitle()
                )
                .screenId(
                        show.getScreen().getId()
                )
                .screenName(
                        show.getScreen().getName()
                )
                .theaterId(
                        show.getScreen()
                                .getTheater()
                                .getId()
                )
                .theaterName(
                        show.getScreen()
                                .getTheater()
                                .getName()
                )
                .startTime(
                        show.getStartTime()
                )
                .endTime(
                        show.getEndTime()
                )
                .basePrice(
                        show.getBasePrice()
                )
                .status(
                        show.getStatus()
                )
                .build();
    }

    private ShowSeatResponse mapShowSeatResponse(
            ShowSeat showSeat
    ) {

        return ShowSeatResponse.builder()
                .showSeatId(
                        showSeat.getId()
                )
                .seatId(
                        showSeat.getSeat().getId()
                )
                .seatNumber(
                        showSeat.getSeat().getSeatNumber()
                )
                .rowLabel(
                        showSeat.getSeat().getRowLabel()
                )
                .seatIndex(
                        showSeat.getSeat().getSeatIndex()
                )
                .seatType(
                        showSeat.getSeat().getSeatType()
                )
                .price(
                        showSeat.getPrice()
                )
                .status(
                        showSeat.getStatus()
                )
                .build();
    }
}
