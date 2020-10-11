package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Model object that represents clients data.
 */
@Builder
@Value
@JsonDeserialize(builder = Clients.ClientsBuilder.class)
@JsonSerialize(as = Clients.class)
public class Clients {

    @NonNull
    @JsonProperty("client")
    ImmutableList<Client> client;
}
