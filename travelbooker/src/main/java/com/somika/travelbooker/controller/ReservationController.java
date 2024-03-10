package com.somika.travelbooker.controller;

import com.somika.travelbooker.dto.ReservationDto;
import com.somika.travelbooker.dto.request.ReservationRequestDto;
import com.somika.travelbooker.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationDto createReservation(@RequestBody ReservationRequestDto reservationRequest) {
        ReservationDto reservation = reservationService.createReservation(reservationRequest);
        log.info("New reservation is created: {}", reservation);
        return reservation;
    }

    @DeleteMapping("/{reservationId}")
    public void cancelReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.cancelReservation(reservationId);
        log.info("Reservation with id {} is canceled", reservationId);
    }
}
