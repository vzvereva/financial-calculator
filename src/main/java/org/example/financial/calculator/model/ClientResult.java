package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

/**
 * Model object that represents calculator result for single client.
 */
@Builder
@Value
@JsonDeserialize(builder = ClientResult.ClientResultBuilder.class)
@JsonSerialize(as = ClientResult.class)
public class ClientResult {

    @JsonProperty("clientInfo")
    ClientInfo clientInfo;

    @JsonProperty("calculatonResult")
    CalculationResult calculationResult;
}
