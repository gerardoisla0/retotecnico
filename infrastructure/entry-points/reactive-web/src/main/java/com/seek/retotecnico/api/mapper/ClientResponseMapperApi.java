package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.dto.response.CreateCustomerResponseApi;
import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface ClientResponseMapperApi {

    ClientResponseMapperApi MAPPER = Mappers.getMapper(ClientResponseMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateCustomerResponseApi requestToResponse(
            CreateCustomerRequestApi createCustomerRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "saveCustomerRS.name", source = "customer.name")
    @Mapping(target = "saveCustomerRS.lastName", source = "customer.lastName")
    @Mapping(target = "saveCustomerRS.documentId", source = "customer.documentId")
    @Mapping(target = "saveCustomerRS.age", source = "customer.age")
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateCustomerResponseApi clientToCreateUserResponse(Customer customer, TechnicalMessage technicalMessage);

    @Mapping(target = "customerMetricsRS.totalCustomers", source = "customerMetrics.totalCustomers")
    @Mapping(target = "customerMetricsRS.averageAge", source = "customerMetrics.averageAge")
    @Mapping(target = "customerMetricsRS.ageStandardDeviation", source = "customerMetrics.ageStandardDeviation")
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateCustomerResponseApi metricsToMetricsResponse(CustomerMetrics customerMetrics, TechnicalMessage technicalMessage);

    @Mapping(target = "listCustomerRS.customers", source = "customers")
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateCustomerResponseApi listToCustomerResponse(List<Customer> customers, TechnicalMessage technicalMessage);
}
