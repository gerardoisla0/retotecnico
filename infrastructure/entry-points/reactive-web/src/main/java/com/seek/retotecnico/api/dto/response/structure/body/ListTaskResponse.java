package com.seek.retotecnico.api.dto.response.structure.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import com.seek.retotecnico.model.task.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListTaskResponse {

    @JsonProperty("tasks")
    @Schema(description = SchemaConstantsApi.RESPONSE_ID)
    List<Task> tasks;
}
