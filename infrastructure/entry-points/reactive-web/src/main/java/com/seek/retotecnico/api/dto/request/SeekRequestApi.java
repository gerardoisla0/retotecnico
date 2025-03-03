package com.seek.retotecnico.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.dto.request.body.SaveCustomerRequest;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = SchemaConstantsApi.REQUEST_TITLE)
public class CreateCustomerRequestApi {

    @JsonProperty("saveCustomerRQ")
    @Schema(description = SchemaConstantsApi.REQUEST_SAVE_CUSTOMER)
    SaveCustomerRequest saveCustomerRQ;

}
