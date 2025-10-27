package com.deliverytouroptimizer.dto;

import com.deliverytouroptimizer.model.enums.DeliveryStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryDTO {

    private Long id;

    @NotBlank(message = "Delivery address cannot be blank")
    private String address;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
    private Double longitude;

    @Min(value = 0, message = "Weight must be zero or positive")
    private double weight;

    @Min(value = 0, message = "Volume must be zero or positive")
    private double volume;

    private String preferredTimeSlot; // optional

    @NotNull(message = "Delivery status is required")
    private DeliveryStatus status;

    @NotNull(message = "Tour ID is required")
    private Long tourId;
}
