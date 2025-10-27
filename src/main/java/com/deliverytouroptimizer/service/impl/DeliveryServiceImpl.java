package com.deliverytouroptimizer.service.impl;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.exception.ResourceNotFoundException;
import com.deliverytouroptimizer.mapper.DeliveryMapper;
import com.deliverytouroptimizer.model.Delivery;
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
            // set tour if provided
            if (dto.getTourId() != null) {
                delivery.setTour(tourRepository.findById(dto.getTourId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tour not found")));
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
}
