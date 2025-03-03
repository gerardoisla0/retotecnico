package com.seek.retotecnico.api.mapper;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.api.dto.response.structure.body.AuthResponseApi;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})

public interface GenerateTokenMapperApi {

    GenerateTokenMapperApi MAPPER = Mappers.getMapper(GenerateTokenMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi requestToResponse(TechnicalMessage technicalMessage);

    @Mapping(target = "authUserRS", source = "authResponseApi")
    @Mapping(target = "message", source = "technicalMessage.message")
    SeekResponseApi requestToGetGenerateTokenResponse(AuthResponseApi authResponseApi, TechnicalMessage technicalMessage);

}