package com.deliverytouroptimizer.service.impl;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.mapper.DeliveryMapper;
import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.Tour;
import com.deliverytouroptimizer.model.Vehicle;
import com.deliverytouroptimizer.model.enums.DeliveryStatus;
import com.deliverytouroptimizer.model.enums.VehicleType;
import com.deliverytouroptimizer.repository.DeliveryRepository;
import com.deliverytouroptimizer.repository.TourRepository;
import com.deliverytouroptimizer.service.DeliveryService;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final TourRepository tourRepository;
    private final DeliveryMapper deliveryMapper;
    private final TransactionTemplate transactionTemplate;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               TourRepository tourRepository,
                               DeliveryMapper deliveryMapper,
                               TransactionTemplate transactionTemplate) {
        this.deliveryRepository = deliveryRepository;
        this.tourRepository = tourRepository;
        this.deliveryMapper = deliveryMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public DeliveryDTO create(DeliveryDTO dto) {
        return transactionTemplate.execute(status -> {
            Delivery delivery = deliveryMapper.toEntity(dto);

            if (dto.getTourId() != null) {
                Tour tour = tourRepository.findById(dto.getTourId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tour not found"));
                validateVehicleCapacity(tour, delivery);
                delivery.setTour(tour);
            }

            Delivery saved = deliveryRepository.save(delivery);
            return deliveryMapper.toDTO(saved);
        });
    }

    @Override
    public DeliveryDTO update(Long id, DeliveryDTO dto) {
        return transactionTemplate.execute(status -> {
            Delivery delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

            delivery.setAddress(dto.getAddress());
            delivery.setLatitude(dto.getLatitude());
            delivery.setLongitude(dto.getLongitude());
            delivery.setWeight(dto.getWeight());
            delivery.setVolume(dto.getVolume());
            delivery.setPreferredTimeSlot(dto.getPreferredTimeSlot());
            delivery.setStatus(dto.getStatus());

            Delivery updated = deliveryRepository.save(delivery);
            return deliveryMapper.toDTO(updated);
        });
    }

    @Override
    public void delete(Long id) {
        transactionTemplate.executeWithoutResult(status -> {
            if (!deliveryRepository.existsById(id)) {
                throw new ResourceNotFoundException("Delivery not found");
            }
            deliveryRepository.deleteById(id);
        });
    }

    @Override
    public DeliveryDTO getById(Long id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
    }

    @Override
    public List<DeliveryDTO> getAll() {
        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDTO updateStatus(Long id, DeliveryStatus newStatus) {
        return transactionTemplate.execute(status -> {
            Delivery delivery = deliveryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

            if (delivery.getStatus() == DeliveryStatus.DELIVERED || delivery.getStatus() == DeliveryStatus.FAILED) {
                throw new IllegalStateException("Cannot change status after delivery is completed or failed.");
            }

            delivery.setStatus(newStatus);
            Delivery updated = deliveryRepository.save(delivery);
            return deliveryMapper.toDTO(updated);
        });
    }

    @Override
    public DeliveryDTO assignToTour(Long deliveryId, Long tourId) {
        return transactionTemplate.execute(status -> {
            Delivery delivery = deliveryRepository.findById(deliveryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));

            Tour tour = tourRepository.findById(tourId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tour not found"));

            validateVehicleCapacity(tour, delivery);

            delivery.setTour(tour);
            Delivery updated = deliveryRepository.save(delivery);
            return deliveryMapper.toDTO(updated);
        });
    }

    private void validateVehicleCapacity(Tour tour, Delivery newDelivery) {
        Vehicle vehicle = tour.getVehicle();
        if (vehicle == null) return;

        double totalWeight = tour.getDeliveries().stream().mapToDouble(Delivery::getWeight).sum() + newDelivery.getWeight();
        double totalVolume = tour.getDeliveries().stream().mapToDouble(Delivery::getVolume).sum() + newDelivery.getVolume();
        int totalDeliveries = tour.getDeliveries().size() + 1;

        double maxWeight, maxVolume;
        int maxDeliveries;

        switch (vehicle.getType()) {
            case BIKE -> {
                maxWeight = 50;
                maxVolume = 0.5;
                maxDeliveries = 15;
            }
            case VAN -> {
                maxWeight = 1000;
                maxVolume = 8;
                maxDeliveries = 50;
            }
            case TRUCK -> {
                maxWeight = 5000;
                maxVolume = 40;
                maxDeliveries = 100;
            }
            default -> throw new IllegalArgumentException("Unknown vehicle type");
        }

        if (totalWeight > maxWeight || totalVolume > maxVolume || totalDeliveries > maxDeliveries) {
            throw new IllegalArgumentException("Vehicle capacity exceeded for this delivery assignment");
        }
    }
}
