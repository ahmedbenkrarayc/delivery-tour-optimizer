package com.deliverytouroptimizer.repository;

import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.Tour;
import com.deliverytouroptimizer.model.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStatus(DeliveryStatus status);
}
