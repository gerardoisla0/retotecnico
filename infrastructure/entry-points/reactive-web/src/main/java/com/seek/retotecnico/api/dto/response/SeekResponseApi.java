package com.seek.retotecnico.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.dto.response.structure.body.AuthResponseApi;
import com.seek.retotecnico.api.dto.response.structure.body.ListTaskResponse;
import com.seek.retotecnico.api.dto.response.structure.body.SaveCustomerResponse;
import com.seek.retotecnico.api.dto.response.structure.body.SaveTaskResponse;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = SchemaConstantsApi.RESPONSE_TITLE)
public class SeekResponseApi {

    @JsonProperty("listTaskRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_LIST_CUSTOMER)
    ListTaskResponse listTaskRS;

    @JsonProperty("saveUserRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_SAVE_CUSTOMER)
    SaveCustomerResponse saveUserRS;

    @JsonProperty("saveTaskRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_SAVE_CUSTOMER)
    SaveTaskResponse saveTaskRS;

    @JsonProperty("authUserRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_SAVE_CUSTOMER)
    AuthResponseApi authUserRS;

    @JsonProperty("message")
    @Schema(description = SchemaConstantsApi.RESPONSE_MESSAGE)
    String message;
}