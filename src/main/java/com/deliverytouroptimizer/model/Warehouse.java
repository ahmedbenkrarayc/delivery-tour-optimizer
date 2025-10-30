package com.deliverytouroptimizer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Warehouse name cannot be blank")
    private String name;

    @NotBlank(message = "Warehouse address cannot be blank")
    private String address;

    @NotNull(message = "Warehouse opening time is required")
    private LocalTime openingTime;

    @NotNull(message = "Warehouse closing time is required")
    private LocalTime closingTime;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("warehouse-tours")
    private List<Tour> tours;
}
