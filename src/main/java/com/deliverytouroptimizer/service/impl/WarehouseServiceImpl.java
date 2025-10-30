package com.deliverytouroptimizer.service.impl;

import com.deliverytouroptimizer.dto.WarehouseDTO;
import com.deliverytouroptimizer.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.mapper.WarehouseMapper;
import com.deliverytouroptimizer.model.Warehouse;
import com.deliverytouroptimizer.repository.WarehouseRepository;
import com.deliverytouroptimizer.service.WarehouseService;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final TransactionTemplate transactionTemplate;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                WarehouseMapper warehouseMapper,
                                TransactionTemplate transactionTemplate) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public WarehouseDTO create(WarehouseDTO dto) {
        return transactionTemplate.execute(status -> {
            Warehouse warehouse = warehouseMapper.toEntity(dto);
            Warehouse saved = warehouseRepository.save(warehouse);
            return warehouseMapper.toDTO(saved);
        });
    }

    @Override
    public WarehouseDTO update(Long id, WarehouseDTO dto) {
        return transactionTemplate.execute(status -> {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + id));

            warehouse.setName(dto.getName());
            warehouse.setAddress(dto.getAddress());
            warehouse.setOpeningTime(dto.getOpeningTime());
            warehouse.setClosingTime(dto.getClosingTime());

            Warehouse updated = warehouseRepository.save(warehouse);
            return warehouseMapper.toDTO(updated);
        });
    }

    @Override
    public void delete(Long id) {
        transactionTemplate.executeWithoutResult(status -> {
            if (!warehouseRepository.existsById(id)) {
                throw new ResourceNotFoundException("Warehouse not found: " + id);
            }
            warehouseRepository.deleteById(id);
        });
    }

    @Override
    public WarehouseDTO getById(Long id) {
        return warehouseRepository.findById(id)
                .map(warehouseMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + id));
    }

    @Override
    public List<WarehouseDTO> getAll() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
