package com.deliverytouroptimizer.service;

import com.deliverytouroptimizer.model.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery);
    Delivery updateDelivery(Long id, Delivery delivery);
    void deleteDelivery(Long id);
    List<Delivery> getAllDeliveries();
    Optional<Delivery> getDeliveryById(Long id);
}
