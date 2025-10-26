package com.deliverytouroptimizer.repository;

import com.deliverytouroptimizer.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}