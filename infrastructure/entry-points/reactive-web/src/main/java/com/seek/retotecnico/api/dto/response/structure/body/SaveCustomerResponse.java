package com.seek.retotecnico.api.dto.response.structure.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveCustomerResponse {

    @JsonProperty("name")
    @Schema(description = SchemaConstantsApi.RESPONSE_ID)
    String name;

    @JsonProperty("lastName")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    String lastName;

    @JsonProperty("documentId")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    String documentId;

    @JsonProperty("age")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    Integer age;
}
