package com.seek.retotecnico.mysql.mapper;

import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.mysql.model.CustomerData;
import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.mysql.model.CustomerMetricsData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
    @Mapping(target = "name", source = "customer.name")
    @Mapping(target = "lastName", source = "customer.lastName")
    @Mapping(target = "documentId", source = "customer.documentId")
    @Mapping(target = "age", source = "customer.age")
    @Mapping(target = "birthDay", source = "customer.birthDay")
    CustomerData domainToData(Customer customer);
    @Mapping(target = "name", source = "customerData.name")
    @Mapping(target = "lastName", source = "customerData.lastName")
    @Mapping(target = "documentId", source = "customerData.documentId")
    @Mapping(target = "age", source = "customerData.age")
    @Mapping(target = "birthDay", source = "customerData.birthDay")
    Customer dataToDomain(CustomerData customerData);

    @Mapping(target = "totalCustomers", source = "customerMetricsData.totalCustomers")
    @Mapping(target = "averageAge", source = "customerMetricsData.averageAge")
    @Mapping(target = "ageStandardDeviation", source = "customerMetricsData.ageStandardDeviation")
    CustomerMetrics metricsToDomain(CustomerMetricsData customerMetricsData);
}
