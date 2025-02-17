package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.model.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface ClientRequestMapperApi {

    ClientRequestMapperApi MAPPER = Mappers.getMapper(ClientRequestMapperApi.class);
    @Mapping(target = "name", source = "createCustomerRequestApi.saveCustomerRQ.name")
    @Mapping(target = "lastName", source = "createCustomerRequestApi.saveCustomerRQ.lastName")
    @Mapping(target = "documentId", source = "createCustomerRequestApi.saveCustomerRQ.documentId")
    @Mapping(target = "age", source = "createCustomerRequestApi.saveCustomerRQ.age")
    @Mapping(target = "birthDay", source = "createCustomerRequestApi.saveCustomerRQ.birthDay", qualifiedByName = "stringToLocalDate")
    Customer createCustomerRequestToClient(CreateCustomerRequestApi createCustomerRequestApi);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }
}
