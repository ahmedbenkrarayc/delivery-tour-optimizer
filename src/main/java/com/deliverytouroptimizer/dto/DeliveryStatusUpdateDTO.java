package com.deliverytouroptimizer.dto;

import com.deliverytouroptimizer.model.enums.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private DeliveryStatus status;
}
