package com.deliverytouroptimizer.service;

import com.deliverytouroptimizer.dto.AssignDeliveriesDTO;
import com.deliverytouroptimizer.dto.TourDTO;

import java.util.List;

public interface TourService {
    TourDTO create(TourDTO dto);
    TourDTO update(Long id, TourDTO dto);
    void delete(Long id);
    TourDTO getById(Long id);
    List<TourDTO> getAll();
    TourDTO assignDeliveries(AssignDeliveriesDTO dto);
}
