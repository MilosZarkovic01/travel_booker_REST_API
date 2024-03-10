package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.TravelPlanDto;

import java.time.LocalDate;
import java.util.List;

public interface TravelPlanService {
    List<TravelPlanDto> getTravelPlansForDestination(Long destinationId);

    List<TravelPlanDto> findTravelPlansByDestinationFromTo(Long destinationId, LocalDate dateFrom, LocalDate dateTo);
}
