package com.deliverytouroptimizer.controller;

import com.deliverytouroptimizer.dto.AssignDeliveriesDTO;
import com.deliverytouroptimizer.dto.TourDTO;
import com.deliverytouroptimizer.service.TourService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@Valid @RequestBody TourDTO dto) {
        TourDTO created = tourService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @Valid @RequestBody TourDTO dto) {
        TourDTO updated = tourService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTour(@PathVariable Long id) {
        TourDTO dto = tourService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        List<TourDTO> tours = tourService.getAll();
        return ResponseEntity.ok(tours);
    }

    @PostMapping("/assign-deliveries")
    public ResponseEntity<TourDTO> assignDeliveries(@Valid @RequestBody AssignDeliveriesDTO dto) {
        TourDTO updatedTour = tourService.assignDeliveries(dto);
        return ResponseEntity.ok(updatedTour);
    }
}
