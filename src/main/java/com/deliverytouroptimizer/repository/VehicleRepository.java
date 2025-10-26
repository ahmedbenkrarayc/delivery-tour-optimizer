package com.deliverytouroptimizer.repository;

import com.deliverytouroptimizer.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
