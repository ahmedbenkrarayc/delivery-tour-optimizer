package com.deliverytouroptimizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TourDTO {
    private Long id;

    @NotNull(message = "Tour date is required")
    private LocalDate date;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @Size(max = 200, message = "A tour cannot have more than 200 deliveries in request")
    private List<Long> deliveryIds;
}
