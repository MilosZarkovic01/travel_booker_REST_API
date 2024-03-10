package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.DestinationDto;
import com.somika.travelbooker.dto.request.SearchRequestDto;

import java.util.List;

public interface DestinationService {
    List<DestinationDto> searchDestinationsByCountryOrCity(SearchRequestDto searchRequest);

    DestinationDto getDestinationById(Long destinationId);
}
