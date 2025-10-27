package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.model.Delivery;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "tour.id", target = "tourId")
    DeliveryDTO toDTO(Delivery delivery);

    @Mapping(target = "tour", ignore = true)
    Delivery toEntity(DeliveryDTO dto);
}
