package com.deliverytouroptimizer.service.impl;

import com.deliverytouroptimizer.dto.VehicleDTO;
import com.deliverytouroptimizer.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.mapper.VehicleMapper;
import com.deliverytouroptimizer.model.Vehicle;
import com.deliverytouroptimizer.model.enums.VehicleType;
import com.deliverytouroptimizer.repository.VehicleRepository;
import com.deliverytouroptimizer.service.VehicleService;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final TransactionTemplate transactionTemplate;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              VehicleMapper vehicleMapper,
                              TransactionTemplate transactionTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public VehicleDTO create(VehicleDTO dto) {
        return transactionTemplate.execute(status -> {
            Vehicle entity = vehicleMapper.toEntity(dto);
            Vehicle saved = vehicleRepository.save(entity);
            return vehicleMapper.toDTO(saved);
        });
    }

    @Override
    public VehicleDTO update(Long id, VehicleDTO dto) {
        return transactionTemplate.execute(status -> {
            Vehicle existing = vehicleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id " + id));

            existing.setType(dto.getType());
            existing.setMaxWeight(dto.getMaxWeight());
            existing.setMaxVolume(dto.getMaxVolume());
            existing.setMaxDeliveries(dto.getMaxDeliveries());

            Vehicle updated = vehicleRepository.save(existing);
            return vehicleMapper.toDTO(updated);
        });
    }

    @Override
    public void delete(Long id) {
        transactionTemplate.executeWithoutResult(status -> {
            if (!vehicleRepository.existsById(id)) {
                throw new ResourceNotFoundException("Vehicle not found with id " + id);
            }
            vehicleRepository.deleteById(id);
        });
    }

    @Override
    public VehicleDTO getById(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id " + id));
    }

    @Override
    public List<VehicleDTO> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> getByType(VehicleType type) {
        return vehicleRepository.findByType(type)
                .stream()
                .map(vehicleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
