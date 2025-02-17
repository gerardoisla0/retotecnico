package com.seek.retotecnico.mysql.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMetricsData {

    @Column("total_customers")
    protected Long totalCustomers;

    @Column("average_age")
    protected BigDecimal averageAge;

    @Column("age_standard_deviation")
    protected Double ageStandardDeviation;
}

