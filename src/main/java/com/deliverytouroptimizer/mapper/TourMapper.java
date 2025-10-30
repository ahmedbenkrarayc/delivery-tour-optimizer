package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.TourDTO;
import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.Tour;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

public class TourMapper {

    public TourDTO toDTO(Tour tour) {
        if (tour == null) return null;
        TourDTO dto = new TourDTO();
        dto.setId(tour.getId());
        dto.setDate(tour.getDate());
        if (tour.getWarehouse() != null) dto.setWarehouseId(tour.getWarehouse().getId());
        if (tour.getVehicle() != null) dto.setVehicleId(tour.getVehicle().getId());
        if (tour.getDeliveries() != null && Hibernate.isInitialized(tour.getDeliveries())) {
            List<Long> ids = tour.getDeliveries().stream().map(Delivery::getId).collect(Collectors.toList());
            dto.setDeliveryIds(ids);
        }
        return dto;
    }

    public Tour toEntity(TourDTO dto) {
        if (dto == null) return null;
        Tour t = new Tour();
        t.setId(dto.getId());
        t.setDate(dto.getDate());
        return t;
    }
}
