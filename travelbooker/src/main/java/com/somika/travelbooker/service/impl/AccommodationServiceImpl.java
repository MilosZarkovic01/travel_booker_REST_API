package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.AccommodationDto;
import com.somika.travelbooker.mapper.AccommodationMapper;
import com.somika.travelbooker.repository.AccommodationJpaRepository;
import com.somika.travelbooker.repository.entity.AccommodationEntity;
import com.somika.travelbooker.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationJpaRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Override
    public List<AccommodationDto> findAccommodationsByDestination(Long destinationId) {
        List<AccommodationEntity> accommodations = accommodationRepository.findByDestinationDestinationId(destinationId);

        return accommodationMapper.accommodationEntitiesToDtos(accommodations);
    }

}
