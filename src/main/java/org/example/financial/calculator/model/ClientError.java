package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

/**
 * Model object that represents an error caused by invalid client request.
 */
@Builder
@Value
@JsonDeserialize(builder = ClientError.ClientErrorBuilder.class)
@JsonSerialize(as = ClientError.class)
public class ClientError {

    @JsonProperty("field")
    String field;

    @JsonProperty("errorMessage")
    String errorMessage;
}
