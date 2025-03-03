package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.model.user.User;
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
    @Mapping(target = "name", source = "createCustomerRequestApi.saveUserRQ.name")
    @Mapping(target = "lastName", source = "createCustomerRequestApi.saveUserRQ.email")
    @Mapping(target = "documentId", source = "createCustomerRequestApi.saveUserRQ.password")
    User saveUserRequestToClient(SeekRequestApi seekRequestApi);
}
