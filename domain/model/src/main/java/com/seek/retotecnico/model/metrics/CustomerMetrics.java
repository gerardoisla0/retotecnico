package com.seek.retotecnico.model.metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMetrics {
    private Long totalCustomers;
    private BigDecimal averageAge;
    private Double ageStandardDeviation;
}
