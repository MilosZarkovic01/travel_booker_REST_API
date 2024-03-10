package com.somika.travelbooker.mapper;

import com.somika.travelbooker.dto.ReservationDto;
import com.somika.travelbooker.repository.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDto entityToDto(ReservationEntity reservationEntity);

}
