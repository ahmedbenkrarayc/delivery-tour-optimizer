package com.deliverytouroptimizer.model;

import com.deliverytouroptimizer.model.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle type is required")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Min(value = 0, message = "Max weight must be zero or positive")
    private double maxWeight;

    @Min(value = 0, message = "Max volume must be zero or positive")
    private double maxVolume;

    @Min(value = 1, message = "Max deliveries must be at least 1")
    private int maxDeliveries;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Tour> tours;
}
