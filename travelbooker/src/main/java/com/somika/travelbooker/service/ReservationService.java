package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.ReservationDto;
import com.somika.travelbooker.dto.request.ReservationRequestDto;

public interface ReservationService {
    ReservationDto createReservation(ReservationRequestDto reservationRequest);

    void cancelReservation(Long reservationId);
}
