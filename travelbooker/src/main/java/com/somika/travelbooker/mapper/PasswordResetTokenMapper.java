package com.somika.travelbooker.mapper;

import com.somika.travelbooker.model.PasswordResetToken;
import com.somika.travelbooker.repository.entity.PasswordResetTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordResetTokenMapper {

    PasswordResetTokenEntity modelToEntity(PasswordResetToken passwordResetToken);

    PasswordResetToken entityToModel(PasswordResetTokenEntity passwordResetTokenEntity);
}
