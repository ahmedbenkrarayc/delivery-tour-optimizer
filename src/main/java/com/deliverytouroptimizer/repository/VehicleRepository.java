package com.deliverytouroptimizer.repository;

import com.deliverytouroptimizer.model.Vehicle;
import com.deliverytouroptimizer.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByType(VehicleType type);
}
