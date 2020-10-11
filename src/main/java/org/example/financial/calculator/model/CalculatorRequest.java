package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Model object that represents a single message posted by a user of financial calculator.
 */
@Builder
@Value
@JsonDeserialize(builder = CalculatorRequest.CalculatorRequestBuilder.class)
@JsonSerialize(as = CalculatorRequest.class)
public class CalculatorRequest {

    @NonNull
    @JsonProperty("clients")
    Clients clients;
}
