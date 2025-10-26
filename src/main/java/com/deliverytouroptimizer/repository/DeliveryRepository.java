package com.deliverytouroptimizer.repository;

import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryRepository {
    List<Delivery> findByStatus(DeliveryStatus status);
    List<Delivery> findByWarehouseId(Long warehouseId);
}
