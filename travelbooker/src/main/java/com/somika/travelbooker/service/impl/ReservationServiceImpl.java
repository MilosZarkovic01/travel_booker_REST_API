package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.ReservationDto;
import com.somika.travelbooker.dto.request.ReservationRequestDto;
import com.somika.travelbooker.exception.AccountNotFoundException;
import com.somika.travelbooker.exception.NoSeatsAvailableException;
import com.somika.travelbooker.mapper.ReservationMapper;
import com.somika.travelbooker.repository.AccommodationJpaRepository;
import com.somika.travelbooker.repository.AccountJpaRepository;
import com.somika.travelbooker.repository.ReservationJpaRepository;
import com.somika.travelbooker.repository.TravelPlanJpaRepository;
import com.somika.travelbooker.repository.entity.AccommodationEntity;
import com.somika.travelbooker.repository.entity.AccountEntity;
import com.somika.travelbooker.repository.entity.ReservationEntity;
import com.somika.travelbooker.repository.entity.TravelPlanEntity;
import com.somika.travelbooker.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationJpaRepository reservationRepository;
    private final TravelPlanJpaRepository travelPlanRepository;
    private final AccommodationJpaRepository accommodationRepository;
    private final AccountJpaRepository accountRepository;
    private final ReservationMapper reservationMapper;
    private final AccommodationJpaRepository accommodationJpaRepository;

    @Override
    public ReservationDto createReservation(ReservationRequestDto reservationRequest) {
        TravelPlanEntity travelPlan = travelPlanRepository.findById(reservationRequest.travelPlanId())
                .orElseThrow(() -> new RuntimeException("Travel plan not found"));

        if (travelPlan.getNumberOfSeats() == 0) {
            throw new NoSeatsAvailableException("No seats available in the travel plan");
        }

        AccommodationEntity accommodation = accommodationRepository.findById(reservationRequest.accommodationId())
                .orElseThrow();

        AccountEntity account = accountRepository.findById(reservationRequest.accountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account with id %s not found", reservationRequest.accountId())
                ));

        ReservationEntity reservation =
                ReservationEntity.builder()
                        .dateOfReservation(LocalDate.now())
                        .totalCost(calculateTotalAmount(travelPlan, accommodation))
                        .account(account)
                        .accommodation(accommodation)
                        .travelPlan(travelPlan)
                        .build();


        ReservationEntity savedReservation = reservationRepository.save(reservation);
        travelPlan.setNumberOfSeats(travelPlan.getNumberOfSeats() - 1);
        travelPlanRepository.save(travelPlan);

        return reservationMapper.entityToDto(savedReservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private BigDecimal calculateTotalAmount(TravelPlanEntity travelPlan, AccommodationEntity accommodation) {
        BigDecimal planPrice = travelPlan.getPrice();
        BigDecimal accommodationPricePerNight = accommodation.getPricePerNight();

        long numberOfNights = ChronoUnit.DAYS.between(travelPlan.getArrivalDate(), travelPlan.getDepartureDate());

        return planPrice.add(accommodationPricePerNight.multiply(BigDecimal.valueOf(numberOfNights)));
    }
}
