package com.deliverytouroptimizer.mapper;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.model.Delivery;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    DeliveryDTO toDTO(Delivery entity);

    @InheritInverseConfiguration
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    Delivery toEntity(DeliveryDTO dto);
}
