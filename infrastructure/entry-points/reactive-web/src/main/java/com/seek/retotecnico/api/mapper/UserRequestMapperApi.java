package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface UserRequestMapperApi {

    UserRequestMapperApi MAPPER = Mappers.getMapper(UserRequestMapperApi.class);
    @Mapping(target = "name", source = "seekRequestApi.saveUserRQ.name")
    @Mapping(target = "email", source = "seekRequestApi.saveUserRQ.email")
    @Mapping(target = "password", source = "seekRequestApi.saveUserRQ.password")
    User saveUserRequestToClient(SeekRequestApi seekRequestApi);

    @Mapping(target = "saveUserRS.name", source = "user.name")
    @Mapping(target = "saveUserRS.email", source = "user.email")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi clientToCreateUserResponse(User user, TechnicalMessage technicalMessage);
}
