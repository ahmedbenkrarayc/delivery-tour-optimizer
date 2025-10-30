package com.deliverytouroptimizer.service;

import com.deliverytouroptimizer.dto.WarehouseDTO;

import java.util.List;

public interface WarehouseService {

    WarehouseDTO create(WarehouseDTO dto);

    WarehouseDTO update(Long id, WarehouseDTO dto);

    void delete(Long id);

    WarehouseDTO getById(Long id);

    List<WarehouseDTO> getAll();
}
