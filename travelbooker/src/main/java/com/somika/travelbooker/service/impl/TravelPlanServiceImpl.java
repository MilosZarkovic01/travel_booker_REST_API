package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.TravelPlanDto;
import com.somika.travelbooker.mapper.TravelPlanMapper;
import com.somika.travelbooker.repository.TravelPlanJpaRepository;
import com.somika.travelbooker.repository.entity.TravelPlanEntity;
import com.somika.travelbooker.service.TravelPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanJpaRepository travelPlanRepository;
    private final TravelPlanMapper travelPlanMapper;

    @Override
    public List<TravelPlanDto> getTravelPlansForDestination(Long destinationId) {
        return travelPlanMapper.travelPlanEntitiesToDtos(
                travelPlanRepository.findByDestinationDestinationId(destinationId)
        );
    }

    @Override
    public List<TravelPlanDto> findTravelPlansByDestinationFromTo(Long destinationId, LocalDate dateFrom, LocalDate dateTo) {
        List<TravelPlanEntity> travelPlans = travelPlanRepository.findByDestinationDestinationIdAndArrivalDateGreaterThanEqualAndDepartureDateLessThanEqual(destinationId, dateFrom, dateTo);
        return travelPlanMapper.travelPlanEntitiesToDtos(travelPlans);
    }
}
