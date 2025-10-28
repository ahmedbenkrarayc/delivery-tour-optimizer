package com.deliverytouroptimizer.controller;

import com.deliverytouroptimizer.dto.DeliveryDTO;
import com.deliverytouroptimizer.dto.DeliveryStatusUpdateDTO;
import com.deliverytouroptimizer.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(@Valid @RequestBody DeliveryDTO dto) {
        DeliveryDTO created = deliveryService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryDTO dto) {
        DeliveryDTO updated = deliveryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable Long id) {
        DeliveryDTO dto = deliveryService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAll();
        return ResponseEntity.ok(deliveries);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeliveryDTO> updateDeliveryStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryStatusUpdateDTO statusUpdateDTO) {

        DeliveryDTO updated = deliveryService.updateStatus(id, statusUpdateDTO.getStatus());
        return ResponseEntity.ok(updated);
    }
}
