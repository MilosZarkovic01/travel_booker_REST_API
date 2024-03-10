package com.somika.travelbooker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPlanDto {

    private Long travelPlanId;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private BigDecimal price;
    private int numberOfSeats;
}
