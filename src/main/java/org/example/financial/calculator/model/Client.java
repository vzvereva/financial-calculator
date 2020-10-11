package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


/**
 * Model object that represents single client's data.
 */
@Builder
@Value
@JsonDeserialize(builder = Client.ClientBuilder.class)
@JsonSerialize(as = Client.class)
public class Client {

    @JsonProperty("info")
    ClientInfo info;

    @NonNull
    @JsonProperty("balance")
    Balance balance;

    @NonNull
    @JsonProperty("transactions")
    ImmutableList<Transaction> transactions;
}
