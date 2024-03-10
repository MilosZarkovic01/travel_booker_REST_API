package com.somika.travelbooker.controller;

import com.somika.travelbooker.dto.TravelPlanDto;
import com.somika.travelbooker.service.TravelPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/travel-plans")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @GetMapping("/{destinationId}")
    public List<TravelPlanDto> getTravelPlansForDestination(@PathVariable("destinationId") Long destinationId) {
        return travelPlanService.getTravelPlansForDestination(destinationId);
    }

    @GetMapping("/search")
    public List<TravelPlanDto> findTravelPlansByDestinationFromTo(
            @RequestParam("destinationId") Long destinationId,
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        return travelPlanService.findTravelPlansByDestinationFromTo(destinationId, dateFrom, dateTo);
    }
}
