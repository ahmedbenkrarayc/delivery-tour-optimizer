package com.deliverytouroptimizer.service.impl;

import com.deliverytouroptimizer.dto.AssignDeliveriesDTO;
import com.deliverytouroptimizer.dto.TourDTO;
import com.deliverytouroptimizer.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.mapper.TourMapper;
import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.Tour;
import com.deliverytouroptimizer.model.Vehicle;
import com.deliverytouroptimizer.repository.DeliveryRepository;
import com.deliverytouroptimizer.repository.TourRepository;
import com.deliverytouroptimizer.repository.VehicleRepository;
import com.deliverytouroptimizer.repository.WarehouseRepository;
import com.deliverytouroptimizer.service.TourService;
import com.deliverytouroptimizer.service.validation.VehicleCapacityValidator;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final TourMapper tourMapper;
    private final TransactionTemplate transactionTemplate;

    public TourServiceImpl(TourRepository tourRepository,
                           VehicleRepository vehicleRepository,
                           WarehouseRepository warehouseRepository,
                           DeliveryRepository deliveryRepository,
                           TourMapper tourMapper,
                           TransactionTemplate transactionTemplate) {
        this.tourRepository = tourRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
        this.tourMapper = tourMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public TourDTO create(TourDTO dto) {
        return transactionTemplate.execute(status -> {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + dto.getVehicleId()));

            warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + dto.getWarehouseId()));

            List<Delivery> deliveries = (dto.getDeliveryIds() == null) ? new ArrayList<>() :
                    deliveryRepository.findAllById(dto.getDeliveryIds());

            VehicleCapacityValidator.validateVehicleCapacity(vehicle, deliveries);

            Tour tour = new Tour();
            tour.setDate(dto.getDate());
            tour.setVehicle(vehicle);
            tour.setWarehouse(warehouseRepository.findById(dto.getWarehouseId()).get());
            tour.setDeliveries(deliveries);

            deliveries.forEach(d -> d.setTour(tour));

            Tour saved = tourRepository.save(tour);
            return tourMapper.toDTO(saved);
        });
    }

    @Override
    public TourDTO update(Long id, TourDTO dto) {
        return transactionTemplate.execute(status -> {
            Tour tour = tourRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tour not found: " + id));

            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + dto.getVehicleId()));

            warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + dto.getWarehouseId()));

            List<Delivery> deliveries = (dto.getDeliveryIds() == null) ? new ArrayList<>() :
                    deliveryRepository.findAllById(dto.getDeliveryIds());

            VehicleCapacityValidator.validateVehicleCapacity(vehicle, deliveries);

            tour.setDate(dto.getDate());
            tour.setVehicle(vehicle);
            tour.setWarehouse(warehouseRepository.findById(dto.getWarehouseId()).get());
            tour.setDeliveries(deliveries);
            deliveries.forEach(d -> d.setTour(tour));

            Tour updated = tourRepository.save(tour);
            return tourMapper.toDTO(updated);
        });
    }

    @Override
    public void delete(Long id) {
        transactionTemplate.executeWithoutResult(status -> {
            if (!tourRepository.existsById(id)) {
                throw new ResourceNotFoundException("Tour not found: " + id);
            }
            tourRepository.deleteById(id);
        });
    }

    @Override
    public TourDTO getById(Long id) {
        return tourRepository.findById(id).map(tourMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found: " + id));
    }

    @Override
    public List<TourDTO> getAll() {
        return tourRepository.findAll().stream().map(tourMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TourDTO assignDeliveries(AssignDeliveriesDTO dto) {
        return transactionTemplate.execute(status -> {
            Tour tour = tourRepository.findById(dto.getTourId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tour not found: " + dto.getTourId()));

            List<Delivery> deliveries = deliveryRepository.findAllById(dto.getDeliveryIds());

            // Validate capacity including existing deliveries
            List<Delivery> combined = new ArrayList<>();
            if (tour.getDeliveries() != null) combined.addAll(tour.getDeliveries());
            combined.addAll(deliveries);

            VehicleCapacityValidator.validateVehicleCapacity(tour.getVehicle(), combined);

            // Assign
            deliveries.forEach(d -> d.setTour(tour));
            deliveryRepository.saveAll(deliveries);

            if (tour.getDeliveries() == null) tour.setDeliveries(new ArrayList<>());
            tour.getDeliveries().addAll(deliveries);

            Tour updated = tourRepository.save(tour);
            return tourMapper.toDTO(updated);
        });
    }
}
