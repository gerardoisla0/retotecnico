package com.seek.retotecnico.api.dto.response.structure.body;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seek.retotecnico.api.util.constant.SchemaConstantsApi;
import com.seek.retotecnico.model.customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCustomerResponse {

    @JsonProperty("customers")
    @Schema(description = SchemaConstantsApi.RESPONSE_ID)
    List<Customer> customers;
}
