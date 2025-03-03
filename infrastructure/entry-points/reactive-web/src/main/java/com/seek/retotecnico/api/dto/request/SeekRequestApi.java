package com.seek.retotecnico.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.dto.request.body.AuthUserRequest;
import com.seek.retotecnico.api.dto.request.body.SaveTaskRequest;
import com.seek.retotecnico.api.dto.request.body.SaveUserRequest;
import com.seek.retotecnico.api.dto.request.body.UpdateTaskRequest;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = SchemaConstantsApi.REQUEST_TITLE)
public class SeekRequestApi {

    @JsonProperty("saveUserRQ")
    @Schema(description = SchemaConstantsApi.REQUEST_SAVE_CUSTOMER)
    SaveUserRequest saveUserRQ;

    @JsonProperty("saveTaskRQ")
    @Schema(description = SchemaConstantsApi.REQUEST_SAVE_CUSTOMER)
    SaveTaskRequest saveTaskRQ;

    @JsonProperty("updateTaskRQ")
    @Schema(description = SchemaConstantsApi.REQUEST_SAVE_CUSTOMER)
    UpdateTaskRequest updateTaskRQ;

    @JsonProperty("authUserRQ")
    @Schema(description = SchemaConstantsApi.REQUEST_SAVE_CUSTOMER)
    AuthUserRequest authUserRQ;
}
