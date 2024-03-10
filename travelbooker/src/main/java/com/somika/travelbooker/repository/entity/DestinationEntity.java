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

    @OneToMany(mappedBy = "destination")
    private List<AccommodationEntity> accommodations;

    @OneToMany(mappedBy = "destination")
    private List<TravelPlanEntity> travelPlans;

    @Override
    public String toString() {
        return "DestinationEntity{" +
                "destinationId=" + destinationId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
