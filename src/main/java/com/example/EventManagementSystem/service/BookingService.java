package com.example.EventManagementSystem.service;

import com.example.EventManagementSystem.dto.request.BookingRequest;
import com.example.EventManagementSystem.dto.response.BookingDetailsResponse;
import com.example.EventManagementSystem.dto.response.BookingResponse;
import com.example.EventManagementSystem.entity.Booking;
import com.example.EventManagementSystem.entity.BookingSeat;
import com.example.EventManagementSystem.entity.BookingStatus;
import com.example.EventManagementSystem.entity.Show;
import com.example.EventManagementSystem.entity.ShowSeat;
import com.example.EventManagementSystem.entity.ShowSeatStatus;
import com.example.EventManagementSystem.entity.User;
import com.example.EventManagementSystem.exception.BookingAccessDeniedException;
import com.example.EventManagementSystem.exception.InvalidShowSeatException;
import com.example.EventManagementSystem.exception.ResourceNotFoundException;
import com.example.EventManagementSystem.exception.SeatAlreadyBookedException;
import com.example.EventManagementSystem.repository.BookingRepository;
import com.example.EventManagementSystem.repository.BookingSeatRepository;
import com.example.EventManagementSystem.repository.ShowRepository;
import com.example.EventManagementSystem.repository.ShowSeatRepository;
import com.example.EventManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingSeatRepository bookingSeatRepository;

    private final ShowRepository showRepository;

    private final ShowSeatRepository showSeatRepository;

    private final UserRepository userRepository;

    @Transactional
    public BookingResponse createBooking(
            BookingRequest request
    ) {

        User user = getCurrentUser();

        Show show = getShow(request.getShowId());

        List<ShowSeat> showSeats =
                validateShowSeats(
                        show,
                        request.getShowSeatIds()
                );

        for (ShowSeat showSeat : showSeats) {

            if (
                    showSeat.getStatus()
                            != ShowSeatStatus.AVAILABLE
            ) {

                throw new SeatAlreadyBookedException(
                        "Seat "
                                + showSeat.getSeat()
                                .getSeatNumber()
                                + " is not available"
                );
            }
        }

        BigDecimal totalAmount =
                calculateTotalAmount(
                        showSeats
                );

        Booking booking =
                Booking.builder()
                        .user(user)
                        .show(show)
                        .totalAmount(totalAmount)
                        .status(
                                BookingStatus.CONFIRMED
                        )
                        .bookingTime(
                                LocalDateTime.now()
                        )
                        .build();

        Booking savedBooking =
                bookingRepository.save(
                        booking
                );

        List<BookingSeat> bookingSeats =
                showSeats.stream()
                        .map(showSeat ->
                                BookingSeat.builder()
                                        .booking(
                                                savedBooking
                                        )
                                        .showSeat(
                                                showSeat
                                        )
                                        .price(
                                                showSeat.getPrice()
                                        )
                                        .build()
                        )
                        .toList();

        bookingSeatRepository.saveAll(
                bookingSeats
        );

        showSeats.forEach(showSeat ->
                showSeat.setStatus(
                        ShowSeatStatus.BOOKED
                )
        );

        showSeatRepository.saveAll(
                showSeats
        );

        return mapToResponse(
                savedBooking,
                showSeats
        );
    }

    private BigDecimal calculateTotalAmount(
            List<ShowSeat> showSeats
    ) {

        return showSeats.stream()
                .map(ShowSeat::getPrice)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    private Show getShow(
            Long showId
    ) {

        return showRepository
                .findById(showId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: "
                                        + showId
                        )
                );
    }

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
    }

    private BookingResponse mapToResponse(
            Booking booking,
            List<ShowSeat> showSeats
    ) {

        return BookingResponse.builder()
                .bookingId(
                        booking.getId()
                )
                .movieTitle(
                        booking.getShow()
                                .getMovie()
                                .getTitle()
                )
                .theaterName(
                        booking.getShow()
                                .getScreen()
                                .getTheater()
                                .getName()
                )
                .screenName(
                        booking.getShow()
                                .getScreen()
                                .getName()
                )
                .showTime(
                        booking.getShow()
                                .getStartTime()
                )
                .seats(
                        showSeats.stream()
                                .map(showSeat ->
                                        showSeat.getSeat()
                                                .getSeatNumber()
                                )
                                .toList()
                )
                .totalAmount(
                        booking.getTotalAmount()
                )
                .status(
                        booking.getStatus()
                )
                .build();
    }


    private List<ShowSeat> validateShowSeats(
            Show show,
            List<Long> showSeatIds
    ) {

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdIn(
                        showSeatIds
                );

        if (
                showSeats.size()
                        != showSeatIds.size()
        ) {

            throw new InvalidShowSeatException(
                    "One or more selected seats do not exist"
            );
        }

        for (ShowSeat showSeat : showSeats) {

            if (
                    !showSeat.getShow()
                            .getId()
                            .equals(show.getId())
            ) {

                throw new InvalidShowSeatException(
                        "One or more seats do not belong to the selected show"
                );
            }

            if (
                    showSeat.getStatus()
                            != ShowSeatStatus.AVAILABLE
            ) {

                throw new SeatAlreadyBookedException(
                        "Seat "
                                + showSeat.getSeat()
                                .getSeatNumber()
                                + " is not available"
                );
            }
        }

        return showSeats;
    }

    public List<BookingResponse> getMyBookings() {

        User user = getCurrentUser();

        return bookingRepository
                .findByUserIdOrderByBookingTimeDesc(
                        user.getId()
                )
                .stream()
                .map(this::mapBookingToResponse)
                .toList();
    }

    private BookingResponse mapBookingToResponse(
            Booking booking
    ) {

        return BookingResponse.builder()
                .bookingId(
                        booking.getId()
                )
                .movieTitle(
                        booking.getShow()
                                .getMovie()
                                .getTitle()
                )
                .theaterName(
                        booking.getShow()
                                .getScreen()
                                .getTheater()
                                .getName()
                )
                .screenName(
                        booking.getShow()
                                .getScreen()
                                .getName()
                )
                .showTime(
                        booking.getShow()
                                .getStartTime()
                )
                .totalAmount(
                        booking.getTotalAmount()
                )
                .status(
                        booking.getStatus()
                )
                .build();
    }

    public BookingDetailsResponse getBookingDetails(
            Long bookingId
    ) {

        User currentUser =
                getCurrentUser();

        Booking booking =
                bookingRepository.findById(
                                bookingId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found with id: "
                                                + bookingId
                                ));

        if (
                !booking.getUser()
                        .getId()
                        .equals(currentUser.getId())
        ) {

            throw new BookingAccessDeniedException(
                    "You cannot access this booking"
            );
        }

        List<BookingSeat> bookingSeats =
                bookingSeatRepository
                        .findByBookingId(
                                bookingId
                        );

        return BookingDetailsResponse
                .builder()
                .bookingId(
                        booking.getId()
                )
                .customerName(
                        booking.getUser()
                                .getName()
                )
                .movieTitle(
                        booking.getShow()
                                .getMovie()
                                .getTitle()
                )
                .theaterName(
                        booking.getShow()
                                .getScreen()
                                .getTheater()
                                .getName()
                )
                .screenName(
                        booking.getShow()
                                .getScreen()
                                .getName()
                )
                .showTime(
                        booking.getShow()
                                .getStartTime()
                )
                .seats(
                        bookingSeats.stream()
                                .map(seat ->
                                        seat.getShowSeat()
                                                .getSeat()
                                                .getSeatNumber()
                                )
                                .toList()
                )
                .totalAmount(
                        booking.getTotalAmount()
                )
                .status(
                        booking.getStatus()
                )
                .bookingTime(
                        booking.getBookingTime()
                )
                .build();
    }
}
