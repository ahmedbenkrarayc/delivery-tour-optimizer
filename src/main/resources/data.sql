-- =========================
-- Warehouses
-- =========================
INSERT INTO warehouse (id, name, address, opening_time, closing_time)
VALUES
    (1, 'Central Warehouse', '123 Main Street, Casablanca', '06:00', '22:00');

-- =========================
-- Vehicles
-- =========================
INSERT INTO vehicle (id, type, max_weight, max_volume, max_deliveries)
VALUES
    (1, 'BIKE', 50, 0.5, 15),
    (2, 'VAN', 1000, 8, 50),
    (3, 'TRUCK', 5000, 40, 100);

-- =========================
-- Tours
-- =========================
INSERT INTO tour (id, date, vehicle_id, warehouse_id)
VALUES
    (1, '2025-10-26', 1, 1),
    (2, '2025-10-26', 2, 1);

-- =========================
-- Deliveries
-- =========================
INSERT INTO delivery (id, address, latitude, longitude, weight, volume, preferred_time_slot, status, tour_id)
VALUES
    (1, '10 Elm Street', 33.5731, -7.5898, 5, 0.1, '09:00-11:00', 'PENDING', 1),
    (2, '22 Oak Avenue', 33.5745, -7.5905, 8, 0.2, '11:00-13:00', 'PENDING', 1),
    (3, '5 Pine Road', 33.5750, -7.5912, 15, 0.3, '10:00-12:00', 'PENDING', 2),
    (4, '18 Maple Street', 33.5760, -7.5920, 12, 0.5, null, 'PENDING', 2),
    (5, '3 Cedar Lane', 33.5770, -7.5930, 7, 0.1, '14:00-16:00', 'PENDING', 2);
