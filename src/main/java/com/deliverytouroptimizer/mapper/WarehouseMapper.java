package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.WarehouseDTO;
import com.deliverytouroptimizer.model.Tour;
import com.deliverytouroptimizer.model.Warehouse;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseMapper {

    public WarehouseDTO toDTO(Warehouse warehouse) {
        if (warehouse == null) return null;

        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setAddress(warehouse.getAddress());
        dto.setOpeningTime(warehouse.getOpeningTime());
        dto.setClosingTime(warehouse.getClosingTime());

        if (warehouse.getTours() != null && Hibernate.isInitialized(warehouse.getTours())) {
            List<Long> tourIds = warehouse.getTours().stream()
                    .map(Tour::getId)
                    .collect(Collectors.toList());
            dto.setTourIds(tourIds);
        }

        return dto;
    }

    public Warehouse toEntity(WarehouseDTO dto) {
        if (dto == null) return null;

        Warehouse warehouse = new Warehouse();
        warehouse.setId(dto.getId());
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setOpeningTime(dto.getOpeningTime());
        warehouse.setClosingTime(dto.getClosingTime());

        return warehouse;
    }
}
