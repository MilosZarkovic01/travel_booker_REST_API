package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.AccommodationDto;

import java.util.List;

public interface AccommodationService {

    List<AccommodationDto> findAccommodationsByDestination(Long destinationID);

}
