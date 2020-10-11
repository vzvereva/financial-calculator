package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;


/**
 * Model object that represents a response from the financial calculator.
 */
@Builder
@Value
@JsonDeserialize(builder = CalculatorResponse.CalculatorResponseBuilder.class)
@JsonSerialize(as = CalculatorResponse.class)
public class CalculatorResponse {

    @Singular
    @JsonProperty("clients")
    ImmutableList<ClientResult> clients;
}
