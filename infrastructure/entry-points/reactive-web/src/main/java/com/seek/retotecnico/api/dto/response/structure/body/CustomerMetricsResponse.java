package com.seek.retotecnico.api.dto.response.structure.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerMetricsResponse {
    @JsonProperty("totalCustomers")
    @Schema(description = SchemaConstantsApi.RESPONSE_ID)
    String totalCustomers;

    @JsonProperty("averageAge")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    String averageAge;

    @JsonProperty("ageStandardDeviation")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    String ageStandardDeviation;
}
