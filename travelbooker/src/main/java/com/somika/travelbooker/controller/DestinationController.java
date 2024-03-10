package com.somika.travelbooker.controller;

import com.somika.travelbooker.dto.DestinationDto;
import com.somika.travelbooker.dto.request.SearchRequestDto;
import com.somika.travelbooker.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping("/search")
    public List<DestinationDto> searchDestinations(@RequestBody SearchRequestDto searchRequest) {
        return destinationService.searchDestinationsByCountryOrCity(searchRequest);
    }

    @GetMapping("/{destinationId}")
    public DestinationDto getDestinationById(@PathVariable ("destinationId") Long destinationId) {
        return destinationService.getDestinationById(destinationId);
    }
}
