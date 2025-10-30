package com.deliverytouroptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class WarehouseDTO {

    private Long id;

    @NotBlank(message = "Warehouse name cannot be blank")
    private String name;

    @NotBlank(message = "Warehouse address cannot be blank")
    private String address;

    @NotNull(message = "Warehouse opening time is required")
    private LocalTime openingTime;

    @NotNull(message = "Warehouse closing time is required")
    private LocalTime closingTime;

    private List<Long> tourIds;
}
