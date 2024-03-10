package com.somika.travelbooker.repository;

import com.somika.travelbooker.repository.entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationJpaRepository extends JpaRepository<DestinationEntity, Long> {

    List<DestinationEntity> findByCountryOrCity(String country, String city);
}
