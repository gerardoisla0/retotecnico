package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.model.user.User;
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
    SeekResponseApi requestToResponse(
            SeekRequestApi createCustomerRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "saveCustomerRS.name", source = "customer.name")
    @Mapping(target = "saveCustomerRS.lastName", source = "customer.lastName")
    @Mapping(target = "saveCustomerRS.documentId", source = "customer.documentId")
    @Mapping(target = "saveCustomerRS.age", source = "customer.age")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi clientToCreateUserResponse(User user, TechnicalMessage technicalMessage);

    @Mapping(target = "customerMetricsRS.totalCustomers", source = "customerMetrics.totalCustomers")
    @Mapping(target = "customerMetricsRS.averageAge", source = "customerMetrics.averageAge")
    @Mapping(target = "customerMetricsRS.ageStandardDeviation", source = "customerMetrics.ageStandardDeviation")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi metricsToMetricsResponse(CustomerMetrics customerMetrics, TechnicalMessage technicalMessage);

    @Mapping(target = "listCustomerRS.customers", source = "customers")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi listToCustomerResponse(List<User> users, TechnicalMessage technicalMessage);
}
