package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;


/**
 * Model object that represents client details.
 */
@Builder
@Value
@ToString
@JsonDeserialize(builder = ClientInfo.ClientInfoBuilder.class)
@JsonSerialize(as = ClientInfo.class)
public class ClientInfo {

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;
}
