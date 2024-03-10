package com.somika.travelbooker.repository;

import com.somika.travelbooker.repository.entity.AccommodationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationJpaRepository extends JpaRepository<AccommodationEntity, Long> {

    List<AccommodationEntity> findByDestinationDestinationId(Long destinationId);

}
