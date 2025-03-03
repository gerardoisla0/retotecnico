package com.seek.retotecnico.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = SchemaConstantsApi.RESPONSE_TITLE)
@Builder(toBuilder = true)
public class GenerateTokenResponseApi {

    @JsonProperty("token")
    @Schema(description = SchemaConstantsApi.RESPONSE_TOKEN)
    String token;
    @JsonProperty("message")
    @Schema(description = SchemaConstantsApi.RESPONSE_MESSAGE)
    String message;
}
