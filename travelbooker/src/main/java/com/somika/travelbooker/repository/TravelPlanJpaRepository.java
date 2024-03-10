package com.somika.travelbooker.repository;

import com.somika.travelbooker.repository.entity.TravelPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TravelPlanJpaRepository extends JpaRepository<TravelPlanEntity, Long> {

    List<TravelPlanEntity> findByDestinationDestinationId(Long destinationId);

    @Query("select t from TravelPlanEntity t where t.destination.destinationId = ?1 and t.arrivalDate >= ?2 and t.departureDate <= ?3")
    List<TravelPlanEntity> findByDestinationDestinationIdAndArrivalDateGreaterThanEqualAndDepartureDateLessThanEqual(Long destinationId, LocalDate dateFrom, LocalDate dateTo);
}
