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
 * Model object that represents client's balance.
 */
@Builder
@Value
@JsonDeserialize(builder = Balance.BalanceBuilder.class)
@JsonSerialize(as = Balance.class)
public class Balance {

    @NonNull
    @JsonProperty("total")
    BigDecimal total;

    @NonNull
    @JsonProperty("currency")
    String currency;

    @NonNull
    @JsonProperty("date")
    LocalDate date;
}
