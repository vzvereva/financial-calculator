package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Model object that represents a single transaction.
 */
@Builder
@Value
@JsonDeserialize(builder = Transaction.TransactionBuilder.class)
@JsonSerialize(as = Transaction.class)
public class Transaction {

    @NonNull
    @JsonProperty("type")
    TransactionType type;

    @JsonProperty("description")
    String description;

    @NonNull
    @JsonProperty("date")
    LocalDate date;

    @NonNull
    @JsonProperty("value")
    BigDecimal value;

    @NonNull
    @JsonProperty("currency")
    String currency;
}
