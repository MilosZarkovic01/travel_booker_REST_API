package com.somika.travelbooker.mapper;

import com.somika.travelbooker.dto.AccommodationDto;
import com.somika.travelbooker.model.Account;
import com.somika.travelbooker.model.PasswordResetToken;
import com.somika.travelbooker.repository.entity.AccommodationEntity;
import com.somika.travelbooker.repository.entity.AccountEntity;
import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {


    AccommodationDto entityToDto(AccommodationEntity accommodationEntity);

    default List<AccommodationDto> accommodationEntitiesToDtos(List<AccommodationEntity> accommodationEntities) {
        return accommodationEntities.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
