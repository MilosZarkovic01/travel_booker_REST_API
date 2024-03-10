package com.somika.travelbooker.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "travel_plans")
public class TravelPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;

    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private BigDecimal price;
    private int numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private DestinationEntity destination;

    @OneToMany(mappedBy = "travelPlan")
    private List<ReservationEntity> reservations;

    @Override
    public String toString() {
        return "TravelPlanEntity{" +
                "travelPlanId=" + travelPlanId +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", price=" + price +
                ", numberOfSeats=" + numberOfSeats +
                ", destination=" + destination +
                '}';
    }
}
