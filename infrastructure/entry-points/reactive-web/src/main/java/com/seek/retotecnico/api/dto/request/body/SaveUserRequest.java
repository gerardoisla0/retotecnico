package com.seek.retotecnico.api.dto.request.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = SchemaConstantsApi.REQUEST_TITLE)
public class SaveCustomerRequest {
    @JsonProperty("name")
    @Schema(description = SchemaConstantsApi.REQUEST_NAME)
    String name;

    @JsonProperty("lastName")
    @Schema(description = SchemaConstantsApi.REQUEST_LAST_NAME)
    String lastName;

    @JsonProperty("documentId")
    @Schema(description = SchemaConstantsApi.REQUEST_DOCUMENT_ID)
    String documentId;

    @JsonProperty("age")
    @Schema(description = SchemaConstantsApi.REQUEST_AGE)
    Integer age;

    @JsonProperty("birthDay")
    @Schema(description = SchemaConstantsApi.REQUEST_BIRTHDAY)
    String birthDay;
}
