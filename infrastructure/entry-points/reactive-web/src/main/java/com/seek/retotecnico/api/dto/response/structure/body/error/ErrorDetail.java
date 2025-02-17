package com.seek.retotecnico.api.dto.response.structure.body.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder(toBuilder = true)
@Jacksonized
@AllArgsConstructor
public class ErrorDetail {

    @JsonInclude(NON_NULL)
    String message;

}
