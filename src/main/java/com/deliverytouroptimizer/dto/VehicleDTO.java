package com.deliverytouroptimizer.dto;

import com.deliverytouroptimizer.model.enums.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleDTO {
    private Long id;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @Min(value = 0, message = "Max weight must be zero or positive")
    private double maxWeight;

    @Min(value = 0, message = "Max volume must be zero or positive")
    private double maxVolume;

    @Min(value = 1, message = "Max deliveries must be at least 1")
    private int maxDeliveries;
}
