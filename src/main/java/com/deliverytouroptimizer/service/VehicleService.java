package com.deliverytouroptimizer.service;

import com.deliverytouroptimizer.dto.VehicleDTO;
import com.deliverytouroptimizer.model.enums.VehicleType;

import java.util.List;

public interface VehicleService {
    VehicleDTO create(VehicleDTO dto);
    VehicleDTO update(Long id, VehicleDTO dto);
    void delete(Long id);
    VehicleDTO getById(Long id);
    List<VehicleDTO> getAll();
    List<VehicleDTO> getByType(VehicleType type);
}
