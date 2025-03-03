package com.seek.retotecnico.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.dto.response.structure.body.CustomerMetricsResponse;
import com.seek.retotecnico.api.dto.response.structure.body.ListCustomerResponse;
import com.seek.retotecnico.api.dto.response.structure.body.SaveCustomerResponse;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = SchemaConstantsApi.RESPONSE_TITLE)
public class CreateCustomerResponseApi {

    @JsonProperty("customerMetricsRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_CUSTOMER_METRICS)
    CustomerMetricsResponse customerMetricsRS;

    @JsonProperty("listCustomerRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_LIST_CUSTOMER)
    ListCustomerResponse listCustomerRS;

    @JsonProperty("saveCustomerRS")
    @Schema(description = SchemaConstantsApi.RESPONSE_SAVE_CUSTOMER)
    SaveCustomerResponse saveCustomerRS;

    @JsonProperty("message")
    @Schema(description = SchemaConstantsApi.RESPONSE_MESSAGE)
    String message;
}