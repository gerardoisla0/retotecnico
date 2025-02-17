package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})

public interface GenerateTokenMapperApi {

    GenerateTokenMapperApi MAPPER = Mappers.getMapper(GenerateTokenMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    GenerateTokenResponseApi requestToResponse(
            GenerateTokenRequestApi generateTokenRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "message", source = "technicalMessage.message")
    GenerateTokenResponseApi  requestToGetGenerateTokenResponse(
            GenerateTokenRequestApi generateTokenRequestApi, GenerateTokenResponseApi generateTokenResponseApi, TechnicalMessage technicalMessage);

}
