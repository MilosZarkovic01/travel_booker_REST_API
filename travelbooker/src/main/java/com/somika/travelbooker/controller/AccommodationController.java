package com.somika.travelbooker.controller;


import com.somika.travelbooker.dto.AccommodationDto;
import com.somika.travelbooker.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;


    @GetMapping("/{destinationId}")
    public List<AccommodationDto> findsAllAccommodationsForDestination(@PathVariable("destinationId") Long destinationId) {
        return accommodationService.findAccommodationsByDestination(destinationId);
    }
}

