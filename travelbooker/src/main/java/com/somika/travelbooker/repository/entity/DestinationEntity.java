package com.somika.travelbooker.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "destinations")
public class DestinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;

    private String name;
    private String description;
    private String city;
    private String country;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private List<AccommodationEntity> accommodations;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY)
    private List<TravelPlanEntity> travelPlans;
}
