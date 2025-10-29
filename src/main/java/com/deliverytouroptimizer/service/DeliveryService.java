package com.deliverytouroptimizer.service;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.enums.DeliveryStatus;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    DeliveryDTO create(DeliveryDTO dto);
    DeliveryDTO update(Long id, DeliveryDTO dto);
    void delete(Long id);
    DeliveryDTO getById(Long id);
    List<DeliveryDTO> getAll();
    DeliveryDTO updateStatus(Long id, DeliveryStatus newStatus);
    DeliveryDTO assignToTour(Long deliveryId, Long tourId);
}
