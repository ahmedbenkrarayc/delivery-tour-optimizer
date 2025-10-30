package com.deliverytouroptimizer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AssignDeliveriesDTO {

    @NotNull(message = "Tour ID is required")
    private Long tourId;

    @NotEmpty(message = "At least one delivery must be assigned")
    @Size(max = 200, message = "A tour cannot have more than 200 deliveries in a single request")
    private List<Long> deliveryIds;
}
