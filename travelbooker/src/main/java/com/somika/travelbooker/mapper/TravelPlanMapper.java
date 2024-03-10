package com.somika.travelbooker.mapper;

import com.somika.travelbooker.dto.TravelPlanDto;
import com.somika.travelbooker.repository.entity.TravelPlanEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TravelPlanMapper {

    TravelPlanDto entityToDto(TravelPlanEntity travelPlanEntity);

    default List<TravelPlanDto> travelPlanEntitiesToDtos(List<TravelPlanEntity> travelPlanEntity) {
        return travelPlanEntity.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
