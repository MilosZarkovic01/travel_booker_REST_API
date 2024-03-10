package com.somika.travelbooker.service.impl;

import com.somika.travelbooker.dto.DestinationDto;
import com.somika.travelbooker.dto.request.SearchRequestDto;
import com.somika.travelbooker.exception.DestinationNotFoundException;
import com.somika.travelbooker.exception.SearchCriteriaException;
import com.somika.travelbooker.mapper.DestinationMapper;
import com.somika.travelbooker.repository.DestinationJpaRepository;
import com.somika.travelbooker.repository.entity.DestinationEntity;
import com.somika.travelbooker.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationJpaRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    @Override
    public List<DestinationDto> searchDestinationsByCountryOrCity(SearchRequestDto searchRequest) {
        if (searchRequest.country() == null && searchRequest.city() == null) {
            throw new SearchCriteriaException("Please provide at least one search criteria (country or city).");
        }

        return destinationMapper.destinationEntitiesToDtos(
                destinationRepository.findByCountryOrCity(searchRequest.country(), searchRequest.city())
        );
    }

    @Override
    public DestinationDto getDestinationById(Long destinationId) {
        DestinationEntity destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationNotFoundException(
                        String.format("Destination with id %s not found", destinationId)
                ));

        return destinationMapper.entityToDto(destination);
    }
}
