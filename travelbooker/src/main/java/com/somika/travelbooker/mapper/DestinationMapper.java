package com.somika.travelbooker.mapper;

import com.somika.travelbooker.dto.DestinationDto;
import com.somika.travelbooker.repository.entity.DestinationEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DestinationMapper {

    DestinationDto entityToDto(DestinationEntity destinationEntity);

    default List<DestinationDto> destinationEntitiesToDtos(List<DestinationEntity> destinationEntity) {
        return destinationEntity.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
