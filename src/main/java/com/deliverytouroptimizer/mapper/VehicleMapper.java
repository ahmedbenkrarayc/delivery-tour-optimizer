package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.VehicleDTO;
import com.deliverytouroptimizer.model.Vehicle;

public class VehicleMapper {
    public VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) return null;

        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setType(vehicle.getType());
        dto.setMaxWeight(vehicle.getMaxWeight());
        dto.setMaxVolume(vehicle.getMaxVolume());
        dto.setMaxDeliveries(vehicle.getMaxDeliveries());
        return dto;
    }

    public Vehicle toEntity(VehicleDTO dto) {
        if (dto == null) return null;

        return Vehicle.builder()
                .id(dto.getId())
                .type(dto.getType())
                .maxWeight(dto.getMaxWeight())
                .maxVolume(dto.getMaxVolume())
                .maxDeliveries(dto.getMaxDeliveries())
                .build();
    }
}
