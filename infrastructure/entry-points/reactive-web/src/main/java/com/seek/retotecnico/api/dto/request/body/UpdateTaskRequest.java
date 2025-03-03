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
public class UpdateTaskRequest {
    @JsonProperty("title")
    @Schema(description = SchemaConstantsApi.REQUEST_NAME)
    String title;

    @JsonProperty("description")
    @Schema(description = SchemaConstantsApi.REQUEST_LAST_NAME)
    String description;

    @JsonProperty("status")
    @Schema(description = SchemaConstantsApi.REQUEST_DOCUMENT_ID)
    String status;
}