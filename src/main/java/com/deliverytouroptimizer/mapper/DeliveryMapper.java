package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.model.Delivery;

public class DeliveryMapper {

    public DeliveryDTO toDTO(Delivery entity) {
        if (entity == null) return null;
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setWeight(entity.getWeight());
        dto.setVolume(entity.getVolume());
        dto.setPreferredTimeSlot(entity.getPreferredTimeSlot());
        dto.setStatus(entity.getStatus());
        if (entity.getTour() != null) dto.setTourId(entity.getTour().getId());
        return dto;
    }

    public Delivery toEntity(DeliveryDTO dto) {
        if (dto == null) return null;
        Delivery entity = new Delivery();
        entity.setId(dto.getId());
        entity.setAddress(dto.getAddress());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setWeight(dto.getWeight());
        entity.setVolume(dto.getVolume());
        entity.setPreferredTimeSlot(dto.getPreferredTimeSlot());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        return entity;
    }
}
