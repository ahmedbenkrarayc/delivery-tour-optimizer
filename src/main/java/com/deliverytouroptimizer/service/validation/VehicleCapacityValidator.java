package com.deliverytouroptimizer.service.validation;

import com.deliverytouroptimizer.model.Delivery;
import com.deliverytouroptimizer.model.Vehicle;

import java.util.List;

public final class VehicleCapacityValidator {

    private VehicleCapacityValidator() {}

    public static void validateVehicleCapacity(Vehicle vehicle, List<Delivery> deliveries) {
        if (vehicle == null || deliveries == null) return;

        double totalWeight = deliveries.stream().mapToDouble(Delivery::getWeight).sum();
        double totalVolume = deliveries.stream().mapToDouble(Delivery::getVolume).sum();
        int totalDeliveries = deliveries.size();

        double maxWeight;
        double maxVolume;
        int maxDeliveries;

        switch (vehicle.getType()) {
            case BIKE -> {
                maxWeight = 50.0;
                maxVolume = 0.5;
                maxDeliveries = 15;
            }
            case VAN -> {
                maxWeight = 1000.0;
                maxVolume = 8.0;
                maxDeliveries = 50;
            }
            case TRUCK -> {
                maxWeight = 5000.0;
                maxVolume = 40.0;
                maxDeliveries = 100;
            }
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicle.getType());
        }

        if (totalDeliveries > maxDeliveries) {
            throw new IllegalStateException("Too many deliveries for vehicle " + vehicle.getType()
                    + ": " + totalDeliveries + " > " + maxDeliveries);
        }
        if (totalWeight > maxWeight) {
            throw new IllegalStateException("Total weight exceeds vehicle capacity: " + totalWeight + " > " + maxWeight);
        }
        if (totalVolume > maxVolume) {
            throw new IllegalStateException("Total volume exceeds vehicle capacity: " + totalVolume + " > " + maxVolume);
        }
    }
}
