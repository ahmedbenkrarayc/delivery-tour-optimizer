package com.deliverytouroptimizer.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryDTO {

    private Long id;

    @NotBlank(message = "Address is required")
    private String address;

    @Positive(message = "Weight must be positive")
    private double weight;

    @Positive(message = "Volume must be positive")
    private double volume;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
}